package hiof.mobilg11.quizapplication.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue
import hiof.mobilg11.quizapplication.ui.theme.NotificationColor2
import hiof.mobilg11.quizapplication.viewmodels.NotificationViewModel

@Composable
fun NotificationScreen(
    notifications: List<MultiplayerGame>,
    viewModel: NotificationViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(listOf(DeepBlue, Color.Black)))
            .padding(top = 10.dp)
    ) {
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_notifications),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                )
            }
        }
        LazyColumn {
            items(notifications) { game ->
                NotificationCard(
                    game,
                    onAccept = { viewModel.acceptGame(game) },
                    onDecline = { viewModel.declineGame(game) }
                )
            }
        }
    }


}

@Composable
fun NotificationCard(
    game: MultiplayerGame,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = NotificationColor2,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.notification_player_wants_to_play, game.host),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Green, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { onAccept() },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { onDecline() },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}





