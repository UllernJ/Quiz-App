package hiof.mobilg11.quizapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizAppViewModel @Inject constructor(
    private val gameService: GameService,
    userCacheService: UserCacheService
) : ViewModel() {

    private val _gameNotifications: MutableStateFlow<Int> = MutableStateFlow(0)
    val gameNotifications: StateFlow<Int> = _gameNotifications

    val username: String = userCacheService.getUser()!!.username

    init {
        viewModelScope.launch {
            while (true) {
                Log.d("QuizAppViewModel", "Getting notifications...")
                _gameNotifications.value = getGameNotifications(username).size
                delay(5000) //todo change to every 5 minute
            }
        }
    }

    suspend fun getGameNotifications(username: String) = gameService.notifications(username)

}