package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val userService: UserService,
    userCacheService: UserCacheService,
    gameService: GameService
) : ViewModel() {

    val username: String? = userCacheService.getUser().let { user ->
        user?.username
    }

    val games = gameService.getGames(username ?: "")

}