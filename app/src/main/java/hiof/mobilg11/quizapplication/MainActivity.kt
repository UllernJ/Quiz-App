package hiof.mobilg11.quizapplication

import MultiplayerPage
import NavBar
import hiof.mobilg11.quizapplication.ui.pages.QuizPage
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.ui.BottomNavBar
import hiof.mobilg11.quizapplication.ui.pages.HomePage
import hiof.mobilg11.quizapplication.ui.pages.auth.LoginPage
import hiof.mobilg11.quizapplication.ui.pages.ProfilePage
import hiof.mobilg11.quizapplication.ui.pages.auth.RegisterPage
import hiof.mobilg11.quizapplication.ui.pages.SinglePlayerPage
import hiof.mobilg11.quizapplication.ui.pages.ui.theme.QuizApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            QuizApplicationTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationApp()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavigationApp() {
        val navController = rememberNavController()
        val user: User? by remember { mutableStateOf(null) }
        var selectedReference by remember { mutableStateOf<DocumentReference?>(null) }
        var userUid: String? by remember { mutableStateOf(null) }

        Scaffold(
            topBar = {
                NavBar(navController)
            },

            bottomBar = {
                if (!userUid.isNullOrBlank()) {
                    BottomNavBar(navController)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = R.string.login_page_path.toString(),
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(R.string.login_page_path.toString()) {
                    LoginPage(navController) {
                        userUid = doLogin(navController)
                    }
                }
                composable(R.string.register_page_path.toString()) {
                    RegisterPage() {
                        userUid = doLogin(navController)
                    }
                }
                composable(R.string.profile_page_path.toString()) {
                    ProfilePage(navController, user)
                }
                composable(R.string.home_page_path.toString()) {
                    HomePage(navController, user)
                }
                composable(R.string.single_player_path.toString()) {
                    SinglePlayerPage {
                        selectedReference = it
                        navController.navigate(R.string.quiz_page_path.toString())
                    }
                }
                composable(R.string.multiplayer_path.toString()) {
                    MultiplayerPage(navController)
                }
                composable(R.string.quiz_page_path.toString()) {
                    QuizPage(selectedReference)
                }
            }
        }
    }
    private fun doLogin(navController: NavController): String {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        Log.d("MainActivity", "User logged in with uid: $uid")
        navController.navigate(R.string.home_page_path.toString())
        return uid ?: ""
    }
}
