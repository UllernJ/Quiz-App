package hiof.mobilg11.quizapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
        NavHost(navController = navController, startDestination = R.string.login_page_path.toString()) {
            composable(R.string.login_page_path.toString()) {
                LoginPage(navController)
            }
            composable(R.string.register_page_path.toString()) {
                RegisterPage(navController)
            }
        }
    }
}
