package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomNavBarViewModel @Inject constructor(
    private val gameService: GameService,
    userCacheService: UserCacheService
) :
    ViewModel() {
    private val _gameNotifications: MutableStateFlow<Int> = MutableStateFlow(0)
    val gameNotifications: StateFlow<Int> = _gameNotifications
    val username: String = userCacheService.getUser()!!.username
    init {
        viewModelScope.launch {
            _gameNotifications.value = getGameNotifications(username).size
        }
    }

    suspend fun getGameNotifications(username: String) = gameService.notifications(username)


}