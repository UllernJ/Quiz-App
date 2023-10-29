package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.AuthService
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizAppViewModel @Inject constructor(
    private val gameService: GameService,
    private val userService: UserService,
    private val authService: AuthService,
    private val userCacheService: UserCacheService
) : ViewModel() {


    var uuid = authService.currentUserUid
    val notifications: MutableStateFlow<List<MultiplayerGame>> = MutableStateFlow(emptyList())
    val user: MutableStateFlow<User?> = MutableStateFlow(null)
    private var userJob: Job? = null
    private var notificationsJob: Job? = null

    init {
        if (uuid.isNotEmpty()) {
            startUserFlow()
        }
    }

    fun startUserFlow() {
        uuid = authService.currentUserUid
        userJob = viewModelScope.launch {
            userService.get(uuid).cancellable().collect {
                user.value = it
                if((it != null) && (notificationsJob == null)) {
                    startNotificationsFlow()
                }
            }
        }
    }

    private fun startNotificationsFlow() {
        notificationsJob = viewModelScope.launch {
            gameService.notifications(user.value?.username!!).cancellable().collect {
                notifications.value = it
            }
        }
    }

    fun stopFlow() {
        userJob?.cancel()
        user.value = null
        notificationsJob?.cancel()
        notifications.value = emptyList()
    }
}