package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiplayerViewModel @Inject constructor(
    private val gameService: GameService,
    private val userService: UserService,
    private val userCacheService: UserCacheService
) : ViewModel() {

    private val _users = MutableStateFlow<List<User?>>(emptyList())
    val users: MutableStateFlow<List<User?>> = _users

    private val _lastChallengedUsers = MutableStateFlow<List<User>>(emptyList())
    val lastChallengedUsers: MutableStateFlow<List<User>> = _lastChallengedUsers


    private val host: User = userCacheService.getUser()!!

    fun findUser(username: String) {
        viewModelScope.launch {
            _users.value = userService.find(username)
        }
    }
    fun createGame(opponent: User) {
        val game = createGameObject(opponent)
        viewModelScope.launch {
            gameService.create(game)
        }
    }
    private fun createGameObject(opponent: User): MultiplayerGame {
        val uuid = (100000..999999).random().toString()
        return MultiplayerGame(
            uuid = uuid,
            host = host.username,
            opponent = opponent.username
        )
    }

    fun fetchLastChallengedUsers(amount: Int) {
        viewModelScope.launch {
            val usernames = userService.getLastChallengedUsers(host.username, amount)
            val users = usernames.map { username ->
                userService.find(username).first()!!
            }

            _lastChallengedUsers.value = users
        }
    }

}