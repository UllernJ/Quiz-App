package hiof.mobilg11.quizapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.service.QuestionService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val questionService: QuestionService) :
    ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    fun loadQuestions(categoryReference: DocumentReference) {
        viewModelScope.launch {
            Log.d("QuizViewModel", "Loading questions")
            _questions.value =
                questionService.getQuestionsByCategoryReference(categoryReference)
                    .shuffled()
                    .subList(0, 3)
        }
    }

    fun answerQuestion(isCorrect: Boolean) {
        if (isCorrect) {
            _score.value++
        }

        viewModelScope.launch {
            delay(1000L)
            _currentQuestionIndex.value++
        }
    }

    fun restartQuiz() {
        _score.value = 0
        _currentQuestionIndex.value = 0
    }
}
