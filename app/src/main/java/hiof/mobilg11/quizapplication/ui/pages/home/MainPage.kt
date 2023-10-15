package hiof.mobilg11.quizapplication.ui.pages.home

import Alert
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.viewmodels.MainViewModel

@Composable
fun HomePage(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
    user: User? = null
) {
    val isUsernameSet = viewModel.isSet.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val buttonLabels =
            listOf("Singleplayer", "Multiplayer", "Leaderboards", "Settings", "Profile")

        if (!isUsernameSet.value!!) {
            Alert {
               viewModel.setIsSet(true)
            }
        }

        buttonLabels.forEach { label ->
            Button(
                onClick = {
                    when (label) {
                        "Singleplayer" -> navController.navigate(R.string.single_player_path.toString())
                        "Multiplayer" -> navController.navigate(R.string.multiplayer_path.toString())
                        "Leaderboards" -> Log.i("INFO", "Not implemented.");
                        "Settings" -> Log.i("INFO", "Not implemented.");
                        "Profile" -> navController.navigate(R.string.profile_page_path.toString());
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
