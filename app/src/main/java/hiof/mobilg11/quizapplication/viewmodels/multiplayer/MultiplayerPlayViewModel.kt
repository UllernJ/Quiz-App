package hiof.mobilg11.quizapplication.viewmodels.multiplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.CategoryService
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.QuestionService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiplayerPlayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameService: GameService,
    private val categoryService: CategoryService,
    private val questionService: QuestionService
) : ViewModel() {

    private val _game: MutableStateFlow<MultiplayerGame> = MutableStateFlow(MultiplayerGame())
    val game: MutableStateFlow<MultiplayerGame> = _game

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    val categories: MutableStateFlow<List<Category>> = _categories

    private val _questions: MutableStateFlow<List<Question>> = MutableStateFlow(listOf())
    val questions: MutableStateFlow<List<Question>> = _questions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    init {
        viewModelScope.launch {
            val lobbyId = savedStateHandle.get<String>("lobbyId")
            _game.value = if (lobbyId != null) {
                gameService.get(lobbyId) ?: MultiplayerGame()
            } else {
                MultiplayerGame()
            }
            if (_game.value.roundQuestionsReferences.isEmpty()) {
                _categories.value =
                    filterCategoriesPlayed(categoryService.getAllCategories())
                        .shuffled()
                        .subList(0, 3)
            }
        }
    }

    fun questionInit(username: String) {
        if (_game.value.roundQuestionsReferences.isNotEmpty() && !isOurTurnToPick(username)) {
            _questions.value = _game.value.roundQuestionsReferences
        }
    }


    private fun filterCategoriesPlayed(categories: List<Category>): List<Category> {
        return categories.filter { category ->
            !game.value.categoriesPlayedReferences.contains(category.name)
        }
    }

    fun isOurTurnToPick(username: String): Boolean {
        return game.value.roundQuestionsReferences.isEmpty() && game.value.gameState == GameState.WAITING_FOR_HOST && username == game.value.host ||
                game.value.roundQuestionsReferences.isEmpty() && game.value.gameState == GameState.WAITING_FOR_OPPONENT && username == game.value.opponent
    }

    fun fetchQuestions(category: Category) {
        viewModelScope.launch {
            _questions.value =
                questionService.getQuestionsByCategoryName(category.name).subList(0, 3)
        }
        _game.value.categoriesPlayedReferences.add(category.name)
    }

    fun amIOpponent(username: String): Boolean {
        return ((game.value.gameState == GameState.WAITING_FOR_HOST && username == game.value.host) ||
                (game.value.gameState == GameState.WAITING_FOR_OPPONENT && username == game.value.opponent))
    }

    fun answerQuestion(isCorrect: Boolean, username: String) {
        if (isCorrect) {
            if (username == _game.value.host) {
                _game.value.hostScore++
            } else {
                _game.value.opponentScore++
            }
        }
        viewModelScope.launch {
            delay(1000L)
            _currentQuestionIndex.value++
        }
    }

    fun finishRound(username: String) {
        viewModelScope.launch {
            if (isOurTurnToPick(username)) {
                if (_game.value.gameState == GameState.WAITING_FOR_OPPONENT) {
                    _game.value.gameState = GameState.WAITING_FOR_HOST
                } else {
                    _game.value.gameState = GameState.WAITING_FOR_OPPONENT
                }
                _game.value.roundQuestionsReferences.addAll(questions.value)
            } else {
                _game.value.roundIndex++
                if (_game.value.roundIndex == _game.value.numberOfRounds) {
                    _game.value.gameState = GameState.FINISHED
                }
                _game.value.roundQuestionsReferences.clear()
            }
            if (_game.value.gameState == GameState.FINISHED) {
                gameService.end(_game.value)
            } else {
                gameService.update(_game.value)
            }
        }
    }
}
