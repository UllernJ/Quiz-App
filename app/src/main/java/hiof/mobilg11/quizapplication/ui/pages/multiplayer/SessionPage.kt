package hiof.mobilg11.quizapplication.ui.pages.multiplayer;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hiof.mobilg11.quizapplication.dao.GameSessionDao
import hiof.mobilg11.quizapplication.model.GameSession
import hiof.mobilg11.quizapplication.model.UserSession
import hiof.mobilg11.quizapplication.model.UserState
import hiof.mobilg11.quizapplication.model.user.User

@Composable
fun SessionPage() {
    //todo create a game session and save it to the database
    //todo every 5 seconds, check if new players have joined
    //todo when all players are ready, the game can be started

    val game = GameSession(
        players = listOf(
            UserSession(
                User(
                    username = "User 1",
                    uuid = "1"
                ),
                UserState.READY
            ),
            UserSession(
                User(
                    username = "User 2",
                    uuid = "2"
                )
            ),
        ),
        host = User(
            username = "User 1",
            uuid = "1"
        ),
        categoriesPlayed = listOf(),
        questions = listOf()
    )
    var gameId by remember { mutableStateOf("Creating a game...") }
    LaunchedEffect(game) {
        GameSessionDao().createGameSession(game) {
            gameId = it.toString()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Game Session Created!",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Share this code with your friends:",
            style = TextStyle(
                fontSize = 16.sp,
            )
        )
        Text(
            text = gameId,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Players: ${game.players.size}",
            style = TextStyle(
                fontSize = 18.sp,
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        for (player in game.players) {
            Text(
                text = "${player.user.username} - ${transformState(player.state)}",
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
        if (game.players.all { it.state == UserState.READY }) {
            Text(
                text = "All players are ready!",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
            Button(onClick = { /*TODO start game*/ }, modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Start Game")
            }
        } else {
            Text(
                text = "Waiting for all players to be ready...",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

private fun transformState(state: UserState): String {
    if (state == UserState.READY) {
        return "Ready"
    }
    return "Not Ready"
}

