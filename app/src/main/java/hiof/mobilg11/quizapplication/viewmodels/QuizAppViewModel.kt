package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.AuthService
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizAppViewModel @Inject constructor(
    private val gameService: GameService,
    private val userService: UserService,
    private val authService: AuthService,
) : ViewModel() {


    var uuid = authService.currentUserUid
    val notifications: MutableStateFlow<List<MultiplayerGame>> = MutableStateFlow(emptyList())
    val user: MutableStateFlow<User?> = MutableStateFlow(null)
    val isNewNotification: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var userJob: Job? = null
    private var notificationsJob: Job? = null

    init {
        startUserFlow()
    }

    fun startUserFlow() {
        uuid = authService.currentUserUid
        if (uuid.isBlank()) {
            return
        }
        userJob = viewModelScope.launch {
            userService.get(uuid).cancellable().collect {
                user.value = it
                if ((it != null) && (notificationsJob == null)) {
                    startNotificationsFlow(it.username)
                }
            }
        }
    }

    private fun startNotificationsFlow(username: String) {
        notificationsJob = viewModelScope.launch {
            gameService.notifications(username).cancellable().collect {
                val currentNotifications = notifications.value
                notifications.value = it
                if (it.size > currentNotifications.size) {
                    isNewNotification.value = true
                    delay(1000)
                    isNewNotification.value = false
                }
            }
        }
    }

    fun stopFlow() {
        userJob?.cancel()
        user.value = null
        userJob = null
        notificationsJob?.cancel()
        notificationsJob = null
        notifications.value = emptyList()
    }
}