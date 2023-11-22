package hiof.mobilg11.quizapplication.ui.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue
import hiof.mobilg11.quizapplication.viewmodels.LeaderboardViewModel

@Composable
fun LeaderboardScreen(viewModel: LeaderboardViewModel = hiltViewModel()) {

    val stats = viewModel.stats.collectAsState()

    LazyColumn(
        Modifier
            .background(brush = Brush.linearGradient(listOf(DeepBlue, Color.Black)))
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF1C3640))
                    .padding(8.dp),
            ) {
                Text(
                    "#",
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Username",
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Wins",
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Losses",
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Total games",
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }

            stats.value.forEachIndexed { index, playerStats ->
                val backgroundColor =
                    if (index % 2 == 0) Color(0xFF162A31) else Color(0xFF1C3640)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = backgroundColor)
                        .padding(8.dp),
                ) {
                    Text((index + 1).toString(), modifier = Modifier.weight(1f))
                    Text(playerStats.username, modifier = Modifier.weight(1f))
                    Text(playerStats.gamesWon.toString(), modifier = Modifier.weight(1f))
                    Text(playerStats.gamesLost.toString(), modifier = Modifier.weight(1f))
                    Text(playerStats.gamesPlayed.toString(), modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
