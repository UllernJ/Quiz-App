package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {


    fun getGames(username: String): Flow<List<MultiplayerGame>> {
        return gameService.getGames(username)
    }

}