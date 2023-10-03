package hiof.mobilg11.quizapplication

import MultiplayerPage
import NavBar
import QuizPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.ui.pages.HomePage
import hiof.mobilg11.quizapplication.ui.pages.LoginPage
import hiof.mobilg11.quizapplication.ui.pages.ProfilePage
import hiof.mobilg11.quizapplication.ui.pages.RegisterPage
import hiof.mobilg11.quizapplication.ui.pages.SinglePlayerPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavigationApp() {
        val navController = rememberNavController()
        var user: User? by remember { mutableStateOf(null) }

        Scaffold(
            topBar = {
                NavBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = R.string.login_page_path.toString(),
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(R.string.login_page_path.toString()) {
                    LoginPage(navController) { loggedInUser ->
                        user = loggedInUser
                        navController.navigate(R.string.home_page_path.toString())
                    }
                }
                composable(R.string.register_page_path.toString()) {
                    RegisterPage(navController)
                }
                composable(R.string.profile_page_path.toString()) {
                    ProfilePage(navController, user)
                }
                composable(R.string.home_page_path.toString()) {
                    HomePage(navController, user)
                }
                composable(R.string.single_player_path.toString()) {
                    SinglePlayerPage(navController)
                }
                composable(R.string.multiplayer_path.toString()) {
                    MultiplayerPage(navController)
                }
                composable(R.string.quiz_page_path.toString()) {
                    QuizPage()
                }
            }
        }
    }
}
