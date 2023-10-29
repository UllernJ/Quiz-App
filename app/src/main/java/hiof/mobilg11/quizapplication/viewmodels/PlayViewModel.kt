package hiof.mobilg11.quizapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val userService: UserService,
    userCacheService: UserCacheService,
    gameService: GameService
) : ViewModel() {
    private val _isSet: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isSet: StateFlow<Boolean> = _isSet

    val username: String? = userCacheService.getUser().let { user ->
        user?.username
    }
    val games = gameService.getGames(username ?: "")

    init {
        viewModelScope.launch {
            isUsernameSet()
        }
    }

    suspend fun isUsernameSet() {
        viewModelScope.launch {
            _isSet.value = userService.isUsernameSet()
        }
    }

    fun setIsSet(value: Boolean) {
        _isSet.value = value
    }
}