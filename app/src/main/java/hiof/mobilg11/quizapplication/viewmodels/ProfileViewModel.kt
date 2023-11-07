package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val gameService: GameService): ViewModel() {

    private val _games = MutableStateFlow<List<MultiplayerGame>>(emptyList())
    val games: MutableStateFlow<List<MultiplayerGame>> = _games

    fun getStatistics(username: String) {
        viewModelScope.launch {
            _games.value = gameService.getGameStatistics(username)
        }
    }

    fun calculateWinPercentage(games: List<MultiplayerGame>): Int {
        var wins = 0
        var losses = 0
        games.forEach {
            if(it.winner == it.host) {
                wins++
            } else {
                losses++
            }
        }
        return if(wins == 0 && losses == 0) {
            0
        } else {
            (wins.toDouble() / (wins + losses) * 100).toInt()
        }
    }

}