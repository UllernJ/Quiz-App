package hiof.mobilg11.quizapplication.ui.screens.leaderboard

import android.view.RoundedCorner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.model.PlayerStats
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue
import hiof.mobilg11.quizapplication.viewmodels.LeaderboardViewModel

@Composable
fun LeaderboardScreen(viewModel: LeaderboardViewModel = hiltViewModel()) {

    val stats = viewModel.stats.collectAsState().value

    val podiumColors = listOf(
        Color(0xFFD4AF37), // Gold color
        Color(0xFFC9C9C9), // Silver color
        Color(0xFFCD7F32) // Bronze color
    )

    Column(modifier = Modifier
        .fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            stats.getOrNull(1).let {
                if (it != null) {
                    PodiumPlace(it, podiumColors[1], 2)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            stats.getOrNull(0).let {
                if (it != null) {
                    PodiumPlace(it, podiumColors[0], 1)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            stats.getOrNull(2).let {
                if (it != null) {
                    PodiumPlace(it, podiumColors[2], 3)
                }
            }
        }

        LazyColumn {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF1C3640))
                        .padding(8.dp),
                ) {
                    Text(
                        "   #",
                        modifier = Modifier.weight(1f),
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        "  Username",
                        modifier = Modifier.weight(3f),
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        "Wins",
                        modifier = Modifier.weight(2f),
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        "Total games",
                        modifier = Modifier.weight(2f),
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
            }

            itemsIndexed(stats.drop(3).take(7)) { index, playerStats ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "${index + 4}", // The rank will be the index + 4 as the first three are on the podium
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = playerStats.username,
                        modifier = Modifier.weight(3f)
                    )
                    Text(
                        text = "${playerStats.gamesWon} (${playerStats.getWinPercentage().toInt()}%)",
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = "${playerStats.gamesPlayed}",
                        modifier = Modifier.weight(2f)
                    )
                }
            }
        }
    }
}

@Composable
fun PodiumPlace(playerStats: PlayerStats, backgroundColor: Color, place: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = playerStats.username,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Wins: ${playerStats.gamesWon} (${playerStats.getWinPercentage().toInt()}%)",
        )
        Text(
            text = "Games: ${playerStats.gamesPlayed}",
        )

        Surface(
            modifier = Modifier
                .shadow(4.dp, shape = MaterialTheme.shapes.medium)
                .align(Alignment.CenterHorizontally)
                .padding(4.dp),
            color = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .size(
                        width = 100.dp, height = when (place) {
                            1 -> 150.dp
                            2 -> 125.dp
                            else -> 100.dp
                        }
                    )
                    .background(backgroundColor)
            ) {
                Text(
                    text = place.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = when (place) {
                        1 -> 30.sp
                        2 -> 20.sp
                        else -> 17.sp
                    }
                )
            }
        }
    }
}