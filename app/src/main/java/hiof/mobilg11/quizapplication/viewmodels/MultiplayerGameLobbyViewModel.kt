package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MultiplayerGameLobbyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userService: UserService
) : ViewModel() {
    private val _gameId: MutableStateFlow<String> = MutableStateFlow("")
    val gameId: MutableStateFlow<String> = _gameId

    init {
        val categoryName = savedStateHandle.get<String>("lobbyId")
        if (categoryName != null) {
            _gameId.value = categoryName
        }
    }
}