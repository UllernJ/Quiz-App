package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val gameService: GameService
) :
    ViewModel() {
    fun acceptGame(game: MultiplayerGame) {
        viewModelScope.launch {
            gameService.update(game.copy(gameState = GameState.WAITING_FOR_OPPONENT))
        }
    }

    fun declineGame(game: MultiplayerGame) {
        viewModelScope.launch {
            gameService.delete(game.uuid)
        }
    }


}