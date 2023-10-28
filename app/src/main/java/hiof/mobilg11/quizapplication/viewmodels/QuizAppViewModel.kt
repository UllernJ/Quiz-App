package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.GameService
import javax.inject.Inject

@HiltViewModel
class QuizAppViewModel @Inject constructor(
    gameService: GameService
) : ViewModel() {

    val notifications = gameService.notifications

}