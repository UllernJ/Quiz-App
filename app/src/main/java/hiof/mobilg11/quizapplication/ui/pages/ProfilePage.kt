package hiof.mobilg11.quizapplication.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.model.user.User
import hiof.mobilg11.quizapplication.viewmodels.LoginViewModel

@Composable
fun ProfilePage(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val user: User? = viewModel.getUser()

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
            text = if (user != null) "Win percentage: ${user.winPercentage}" else "Win percentage: 0",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = {
                viewModel.signOut()
                navController.navigate(R.string.login_page_path.toString())
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