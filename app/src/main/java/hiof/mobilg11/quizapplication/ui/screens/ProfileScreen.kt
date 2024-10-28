package hiof.mobilg11.quizapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue
import hiof.mobilg11.quizapplication.viewmodels.ProfileViewModel
import kotlin.math.roundToInt

@Composable
fun ProfileScreen(
    user: User?,
    viewModel: ProfileViewModel = hiltViewModel(),
    callback: () -> Unit
) {
    val playerStats = viewModel.playerStats.collectAsState()
    viewModel.getPlayerStats(user?.username ?: "")

    val gamesPlayed = playerStats.value.gamesPlayed
    val gamesWon = playerStats.value.gamesWon
    val gamesLost = playerStats.value.gamesLost
    val gamesDraw = playerStats.value.gamesDraw
    val winRate = if (gamesPlayed > 0) {
        ((gamesWon.toDouble() / gamesPlayed.toDouble()) * 100).roundToInt()
    } else {
        0
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = user?.username ?: stringResource(R.string.profile_user_not_found),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.stats),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    )

                    HorizontalDivider()

                    StatItem(statName = stringResource(R.string.games_played), statValue = "$gamesPlayed")
                    HorizontalDivider(color = Color.LightGray)
                    StatItem(statName = stringResource(R.string.games_won), statValue = "$gamesWon")
                    HorizontalDivider(color = Color.LightGray)
                    StatItem(statName = stringResource(R.string.games_lost), statValue = "$gamesLost")
                    HorizontalDivider(color = Color.LightGray)
                    StatItem(statName = stringResource(R.string.games_draw), statValue = "$gamesDraw")
                    HorizontalDivider(color = Color.LightGray)
                    StatItem(statName = stringResource(R.string.win_rate), statValue = "$winRate%")
                    HorizontalDivider(color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { callback() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.logout),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun StatItem(statName: String, statValue: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = statName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = statValue,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}