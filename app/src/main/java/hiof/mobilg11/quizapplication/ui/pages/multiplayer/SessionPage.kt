package hiof.mobilg11.quizapplication.ui.pages.multiplayer;
//
//import SessionViewModel
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable;
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import hiof.mobilg11.quizapplication.dao.GameSessionDao
//import hiof.mobilg11.quizapplication.model.Game
//import hiof.mobilg11.quizapplication.model.UserSession
//import hiof.mobilg11.quizapplication.model.UserState
//import hiof.mobilg11.quizapplication.model.user.User
//import kotlinx.coroutines.delay
//
//@Composable
//fun SessionPage(gameId: String? = null, user: User? = null) {
//
//    val sessionViewModel: SessionViewModel = viewModel()
//    val game by sessionViewModel.currentGame.collectAsState()
//
//    if (gameId == null && game.players.isEmpty()) {
//        sessionViewModel.createGame(user)
//    } else if (gameId != null && game.players.isEmpty()) {
//        sessionViewModel.joinGame(gameId, user)
//    }
//    val isHost = game.host == user
//    val player = game.players.find { it.user == user }
//
//    LaunchedEffect(game) {
//        sessionViewModel.startFetchingGameData()
//    }
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Game Session",
//            style = TextStyle(
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            ),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        Text(
//            text = "Share this code with your friends:",
//            style = TextStyle(
//                fontSize = 16.sp,
//            )
//        )
//        Text(
//            text = sessionViewModel.gameNumber,
//            style = TextStyle(
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//            ),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        Text(
//            text = "Players: ${game.players.size}",
//            style = TextStyle(
//                fontSize = 18.sp,
//            ),
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        for (player in game.players) {
//            Text(
//                text = "${player.user?.username} - ${transformState(player.state)}",
//                style = TextStyle(
//                    fontSize = 16.sp
//                )
//            )
//        }
//        if (game.players.all { it.state == UserState.READY }) {
//            Text(
//                text = "All players are ready!",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier.padding(top = 16.dp)
//            )
//            if (isHost) {
//                Button(
//                    onClick = { /*TODO start game*/ },
//                    modifier = Modifier.padding(top = 16.dp)
//                ) {
//                    Text(text = "Start Game")
//                }
//            }
//        } else {
//            Text(
//                text = "Waiting for all players to be ready...",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier.padding(top = 16.dp)
//            )
//        }
//        if (isHost) {
//            Button(
//                onClick = { /*TODO delete session*/ },
//                modifier = Modifier.padding(top = 16.dp)
//            ) {
//                Text(text = "Delete session")
//            }
//        }
//        if (player != null && player.state == UserState.NOT_READY) {
//            Button(
//                onClick = {
//                    GameSessionDao().readyUp(sessionViewModel.gameNumber)
//                },
//                modifier = Modifier.padding(top = 16.dp)
//            ) {
//                Text(text = "Ready up")
//            }
//        }
//
//    }
//}
//
//private fun transformState(state: UserState): String {
//    if (state == UserState.READY) {
//        return "Ready"
//    }
//    return "Not Ready"
//}
//
