package hiof.mobilg11.quizapplication.ui.pages.multiplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultiplayerGameLobbyPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Opponent",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "3 - 1",
                fontSize = 40.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "You",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Categories played",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                // Dummy data for categories list
                listOf("*", "*", "*", "*").forEach {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /* navigate to quiz game screen */ }) {
                Text(text = "PLAY")
            }
        }
    }
}