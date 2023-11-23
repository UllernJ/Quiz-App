package hiof.mobilg11.quizapplication.viewmodels.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.PlayerStats
import hiof.mobilg11.quizapplication.service.GameService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(private val gameService: GameService) : ViewModel() {
    private val _stats: MutableStateFlow<List<PlayerStats>> = MutableStateFlow(listOf())
    val stats: MutableStateFlow<List<PlayerStats>> = _stats

    init {
        viewModelScope.launch {
            _stats.value = gameService.getLeaderboardStatistics()
        }
    }
}