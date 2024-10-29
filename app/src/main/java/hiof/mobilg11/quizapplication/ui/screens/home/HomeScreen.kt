package hiof.mobilg11.quizapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.ui.theme.BlueViola1
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue
import hiof.mobilg11.quizapplication.ui.theme.LightBlue
import hiof.mobilg11.quizapplication.ui.theme.LightGreen1
import hiof.mobilg11.quizapplication.ui.theme.OrangeYellow2
import hiof.mobilg11.quizapplication.ui.theme.Purple40
import hiof.mobilg11.quizapplication.ui.theme.PurpleGrey40
import hiof.mobilg11.quizapplication.viewmodels.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    gameNotifications: List<MultiplayerGame>,
    user: User?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(listOf(DeepBlue, Color.Black)))
    ) {
        if (user?.username.isNullOrBlank().not() && user != null) {
            TitleSection(user.username, navController, gameNotifications.size)
            CardSection(
                stringResource(R.string.upcoming_title),
                stringResource(R.string.upcoming_body),
                null,
                null
            )

            CardSection(
                stringResource(R.string.single_player),
                stringResource(R.string.single_player_description),
                Screen.SinglePlayer.route,
                navController,
                LightGreen1
            )
        }


    }
}

@Composable
fun TitleSection(username: String, navController: NavController, gameNotifications: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(brush = Brush.horizontalGradient(listOf(Purple40, PurpleGrey40)))
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.home_welcome_back, username),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(R.string.home_lets_play_quiz),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Column {
            IconButton(
                onClick = { navController.navigate(Screen.Notifications.route) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                    tint = Color.White
                )
                if (gameNotifications > 0) {
                    Badge(
                        content = {
                            Text(text = gameNotifications.toString())
                        },
                        modifier = Modifier.offset(x = 12.dp, y = (-8).dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CardSection(
    title: String,
    description: String,
    route: String?,
    navController: NavController?,
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
                    text = stringResource(R.string.home_start_new_game),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            if (navController != null && route != null) {
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
}


