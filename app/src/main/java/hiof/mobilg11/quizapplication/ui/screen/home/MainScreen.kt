package hiof.mobilg11.quizapplication.ui.screen.home

import Alert
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.ui.theme.BlueViola1
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue
import hiof.mobilg11.quizapplication.ui.theme.LightBlue
import hiof.mobilg11.quizapplication.ui.theme.LightGreen1
import hiof.mobilg11.quizapplication.ui.theme.OrangeYellow2
import hiof.mobilg11.quizapplication.viewmodels.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val isUsernameSet = viewModel.isSet.collectAsState()
    val games = viewModel.games.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(top = 10.dp)
    ) {
        if (!isUsernameSet.value) {
            Alert {
                viewModel.setIsSet(true)
            }
        }
        if (viewModel.username != null && isUsernameSet.value) {
            TitleSection(viewModel.username, navController)
            GameCard(
                "Multiplayer",
                "Play with friends or strangers!",
                Screen.Multiplayer.route,
                navController
            )
            GameCard(
                "Singleplayer",
                "Play alone and get a highscore!",
                Screen.SinglePlayer.route,
                navController,
                LightGreen1
            )
            Games(games.value)
        }


    }
}

@Composable
fun TitleSection(username: String, navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Welcome to back, $username",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Let's play some quizzes!",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Column {
            IconButton(
                onClick = { navController.navigate(Screen.Profile.route) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun GameCard(
    title: String,
    description: String,
    route: String,
    navController: NavController,
    color: Color = OrangeYellow2
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color)
                .padding(horizontal = 15.dp, vertical = 20.dp)
                .fillMaxWidth()

        ) {
            Column {
                Text(
                    text = "Start a new game",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LightBlue)
                    .padding(10.dp),
            ) {
                IconButton(
                    onClick = { navController.navigate(route) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Games(games: List<MultiplayerGame>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Your games",
            style = MaterialTheme.typography.titleMedium,
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            content = { items(games) { GameCard(it) } },
        )
    }
}

@Composable
fun GameCard(
    game: MultiplayerGame,
    viewModel: MainViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BlueViola1)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "You vs ${game.opponent}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Score: ${game.hostScore} - ${game.opponentScore}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (game.gameState == GameState.WAITING_FOR_HOST && game.host == viewModel.username ||
                        game.gameState == GameState.WAITING_FOR_OPPONENT && game.opponent == viewModel.username
                    ) {
                        "Your turn"
                    } else {
                        "Opponent's turn"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                IconButton(
                    onClick = { /*TODO go to game*/ },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(LightBlue)
                        .padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}


