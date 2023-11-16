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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.viewmodels.ProfileViewModel
import kotlin.math.roundToInt

@Composable
fun ProfileScreen(
    user: User?,
    viewModel: ProfileViewModel = hiltViewModel(),
    callback: () -> Unit
) {
    val winPercentage = viewModel.winPercentage.collectAsState()
    viewModel.getWinPercentage(user?.username ?: "")

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.profile),
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
            text = if (user != null) stringResource(R.string.profile_string_display, user.username) else stringResource(
                R.string.profile_user_not_found
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = if (user != null) stringResource(
                R.string.win_percentage,
                winPercentage.value.roundToInt()
            ) else "",
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
                text = stringResource(R.string.logout),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}