package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.UserCacheService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiplayerGameLobbyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameService: GameService,
    private val userCacheService: UserCacheService
) : ViewModel() {
    private val _game: MutableStateFlow<MultiplayerGame> = MutableStateFlow(MultiplayerGame())
    val game: MutableStateFlow<MultiplayerGame> = _game

    val user = userCacheService.getUser()
    init {
        viewModelScope.launch {
            val categoryName = savedStateHandle.get<String>("lobbyId")
            _game.value = if (categoryName != null) {
                gameService.get(categoryName) ?: MultiplayerGame()
            } else {
                MultiplayerGame()
            }
        }
    }
}