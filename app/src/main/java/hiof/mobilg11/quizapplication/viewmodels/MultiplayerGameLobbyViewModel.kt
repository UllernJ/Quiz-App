package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiplayerGameLobbyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameService: GameService,
) : ViewModel() {
    private val _game: MutableStateFlow<MultiplayerGame> = MutableStateFlow(MultiplayerGame())
    val game: MutableStateFlow<MultiplayerGame> = _game

    init {
        viewModelScope.launch {
            val categoryName = savedStateHandle.get<String>("lobbyId")
            _game.value = if (categoryName != null) {
                gameService.get(categoryName) ?: MultiplayerGame()
            } else {
                MultiplayerGame()
            }
        }
    }

    fun abandonGame(username: String) {
        viewModelScope.launch {
            if(!amIOpponent(username)) {
                _game.value.winner = _game.value.opponent
            } else {
                _game.value.winner = _game.value.host
            }
            _game.value.gameState = GameState.FINISHED
            gameService.update(_game.value)
        }
    }

    private fun amIOpponent(username: String): Boolean {
        return ((game.value.gameState == GameState.WAITING_FOR_HOST && username == game.value.host) ||
                (game.value.gameState == GameState.WAITING_FOR_OPPONENT && username == game.value.opponent))
    }

}