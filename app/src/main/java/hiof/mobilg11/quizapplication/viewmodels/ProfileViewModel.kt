package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.PlayerStats
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val gameService: GameService) : ViewModel() {

    /*
    private val _winPercentage = MutableStateFlow(0.0)
    val winPercentage: MutableStateFlow<Double> = _winPercentage
     */
    private val _playerStats = MutableStateFlow(PlayerStats())
    val playerStats: MutableStateFlow<PlayerStats> = _playerStats

    /*
    fun getWinPercentage(username: String) {
        viewModelScope.launch {
            _winPercentage.value = gameService.getWinPercentage(username)
        }
    }
     */

    fun getPlayerStats(username: String) {
        viewModelScope.launch {
            _playerStats.value = gameService.getPlayerStats(username)
        }
    }


}