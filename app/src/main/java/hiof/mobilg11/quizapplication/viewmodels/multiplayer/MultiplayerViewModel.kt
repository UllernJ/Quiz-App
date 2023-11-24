package hiof.mobilg11.quizapplication.viewmodels.multiplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiplayerViewModel @Inject constructor(
    private val gameService: GameService,
    private val userService: UserService
) : ViewModel() {

    private val _users = MutableStateFlow<List<String?>>(emptyList())
    val users: MutableStateFlow<List<String?>> = _users

    private val _lastChallengedUsers = MutableStateFlow<List<String>>(emptyList())
    val lastChallengedUsers: MutableStateFlow<List<String>> = _lastChallengedUsers

    fun findUser(username: String) {
        viewModelScope.launch {
            _users.value = userService.find(username).map { it?.username }
        }
    }

    fun createGame(opponentName: String, host: User, onGameCreated: (Boolean) -> Unit) {
        val game = createGameObject(opponentName, host)
        viewModelScope.launch {
            val result = gameService.create(game)
            onGameCreated(result)
        }
    }

    private fun createGameObject(opponentName: String, host: User): MultiplayerGame {
        return MultiplayerGame(
            host = host.username,
            opponent = opponentName
        )
    }

    fun fetchLastChallengedUsers(username: String) {
        viewModelScope.launch {
            _lastChallengedUsers.value = gameService.getRecentlyPlayedAgainst(username)
        }
    }

}