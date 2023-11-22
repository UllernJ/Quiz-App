package hiof.mobilg11.quizapplication.ui.screens.multiplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue
import hiof.mobilg11.quizapplication.ui.theme.Swords
import hiof.mobilg11.quizapplication.viewmodels.MultiplayerGameLobbyViewModel

@Composable
fun MultiplayerGameLobbyScreen(
    navController: NavController,
    user: User,
    viewModel: MultiplayerGameLobbyViewModel = hiltViewModel(),
) {
    val game = viewModel.game.collectAsState()
    val showDialogue = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(listOf(DeepBlue, Color.Black)))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = Swords),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 10.dp)
            )

            Text(
                text = "${game.value.host} vs ${game.value.opponent}",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Round ${game.value.roundIndex + 1} of ${game.value.numberOfRounds + 1}",
                fontSize = 20.sp,
                color = Color.Gray
            )

            Text(
                text = "Score: ${game.value.hostScore} - ${game.value.opponentScore}",
                fontSize = 20.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (game.value.categoriesPlayedReferences.isNotEmpty()) {
                Text(
                    text = "Categories played",
                    fontSize = 18.sp,
                    color = Color.White
                )
                game.value.categoriesPlayedReferences.forEach {
                    Text(text = it, color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if ((game.value.gameState == GameState.WAITING_FOR_HOST && user.username == game.value.host) ||
                (game.value.gameState == GameState.WAITING_FOR_OPPONENT && user.username == game.value.opponent)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.MultiplayerPlay.createRoute(game.value.uuid)) },
                    modifier = Modifier
                ) {
                    Text(
                        text = "PLAY",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            } else {
                Text(
                    text = "Waiting for opponent to play...",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }

        Button(
            onClick = {
                showDialogue.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(48.dp)
        ) {
            Text(
                text = "Leave game",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }

    if (showDialogue.value) {
        AlertDialog(
            onDismissRequest = {
                showDialogue.value = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.abandonGame(user.username)
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialogue.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "No")
                }
            },
            title = { Text(text = "Are you sure you want to leave the game?") },
            text = { Text(text = "You will lose the game if you leave.") },
        )
    }
}