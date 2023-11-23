package hiof.mobilg11.quizapplication.viewmodels.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameService: GameService
) : ViewModel() {


    fun getGames(username: String): Flow<List<MultiplayerGame>> {
        return gameService.getGames(username)
    }

}