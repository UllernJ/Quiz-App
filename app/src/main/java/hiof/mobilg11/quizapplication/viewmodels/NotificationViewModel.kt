package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val gameService: GameService,
    private val userCacheService: UserCacheService
) :
    ViewModel() {
    private val _notifications: MutableStateFlow<MutableList<MultiplayerGame>> =
        MutableStateFlow(mutableListOf())
    val notifications = _notifications

    init {
        viewModelScope.launch {
            getNotifications()
        }
    }

    private suspend fun getNotifications() {
        userCacheService.getUser()?.let {
            val notifications = gameService.notifications(it.username)
            _notifications.value = notifications.toMutableList()
        }
    }

    fun acceptGame(game: MultiplayerGame) {
        game.gameState = GameState.WAITING_FOR_OPPONENT
        viewModelScope.launch {
            gameService.update(game)
            getNotifications()
        }
    }

    fun declineGame(game: MultiplayerGame) {
        viewModelScope.launch {
            gameService.delete(game.uuid)
            getNotifications()
        }
    }


}