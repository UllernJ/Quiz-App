package hiof.mobilg11.quizapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    user: User?,
    viewModel: ProfileViewModel = hiltViewModel(),
    callback: () -> Unit
) {
    viewModel.getStatistics(user?.username ?: "")
    val games = viewModel.games.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineLarge
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (user != null) "Username: ${user.username}" else "Can't find user",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = if (user != null) "Win percentage: ${
                viewModel.calculateWinPercentage(
                    games.value,
                    user.username
                )
            }%" else "",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = {
                callback()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}