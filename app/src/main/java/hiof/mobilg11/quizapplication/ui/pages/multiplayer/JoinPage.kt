package hiof.mobilg11.quizapplication.ui.pages.multiplayer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.dao.GameSessionDao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinPage(callback: (gameId: String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Join a game with Game ID",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
        var value by remember { mutableStateOf("") }
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Game ID") },
            modifier = Modifier.padding(16.dp),
            singleLine = true,
        )
        Button(
            onClick = {
                GameSessionDao().joinSession(value) {
                    if(it) {
                        callback(value)
                    }
                }
                      },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Join")
        }

    }

}