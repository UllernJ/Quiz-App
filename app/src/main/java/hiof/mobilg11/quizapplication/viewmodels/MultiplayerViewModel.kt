package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
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
    private val _users = MutableStateFlow<List<User?>>(emptyList())
    val users: MutableStateFlow<List<User?>> = _users

    fun findUser(username: String) {
        viewModelScope.launch {
            _users.value = userService.find(username)
        }
    }
}