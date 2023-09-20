package hiof.mobilg11.quizapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hiof.mobilg11.quizapplication.model.User

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationApp()
        }
    }

    @Composable
    fun NavigationApp() {
        val navController = rememberNavController()
        var user: User? by remember { mutableStateOf(null) }

        NavHost(navController = navController, startDestination = R.string.login_page_path.toString()) {
            composable(R.string.login_page_path.toString()) {
                LoginPage(navController) { loggedInUser ->
                    user = loggedInUser
                    navController.navigate(R.string.profile_page_path.toString())
                }
            }
            composable(R.string.register_page_path.toString()) {
                RegisterPage(navController)
            }
            composable(R.string.profile_page_path.toString()) {
                ProfilePage(navController, user)
            }
        }
    }
}
