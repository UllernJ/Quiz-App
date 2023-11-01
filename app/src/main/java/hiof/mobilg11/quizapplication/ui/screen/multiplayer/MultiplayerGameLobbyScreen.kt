package hiof.mobilg11.quizapplication.ui.screen.multiplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.viewmodels.MultiplayerGameLobbyViewModel

@Composable
fun MultiplayerGameLobbyScreen(
    navController: NavController,
    user: User,
    viewModel: MultiplayerGameLobbyViewModel = hiltViewModel(),
) {
    val game = viewModel.game.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game ID: ${game.value.uuid}",
                fontSize = 20.sp,
            )

            Text(
                if (user.username == game.value.host) "${game.value.host} vs ${game.value.opponent}" else "${game.value.opponent} vs ${game.value.host}",
                fontSize = 18.sp,
            )

            Text(
                text = "Round ${game.value.roundIndex} of ${game.value.numberOfRounds}",
                fontSize = 16.sp,
            )

            Text(
                text = "Score: ${game.value.hostScore} - ${game.value.opponentScore}",
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (game.value.categoriesPlayedReferences.isNotEmpty()) {
                Text(
                    text = "Categories played",
                    fontSize = 16.sp,
                )
                Column {
                    game.value.categoriesPlayedReferences.forEach {
                        Text(text = it)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            if ((game.value.gameState == GameState.WAITING_FOR_HOST && user.username == game.value.host) ||
                (game.value.gameState == GameState.WAITING_FOR_OPPONENT && user.username == game.value.opponent)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.MultiplayerPlay.createRoute(game.value.uuid)) },
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                ) {
                    Text(
                        text = "PLAY",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            } else {
                Text(
                    text = "Waiting for opponent to play",
                    fontSize = 18.sp,
                )
            }
        }
    }
}
