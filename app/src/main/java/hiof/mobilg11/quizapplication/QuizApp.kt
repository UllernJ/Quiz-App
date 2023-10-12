package hiof.mobilg11.quizapplication

import MultiplayerPage
import NavBar
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import hiof.mobilg11.quizapplication.dao.UserDao
import hiof.mobilg11.quizapplication.model.user.User
import hiof.mobilg11.quizapplication.ui.navigation.BottomNavBar
import hiof.mobilg11.quizapplication.ui.pages.ProfilePage
import hiof.mobilg11.quizapplication.ui.pages.QuizPage
import hiof.mobilg11.quizapplication.ui.pages.SinglePlayerPage
import hiof.mobilg11.quizapplication.ui.pages.auth.LoginPage
import hiof.mobilg11.quizapplication.ui.pages.auth.RegisterPage
import hiof.mobilg11.quizapplication.ui.pages.home.HomePage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizApp() {
    val navController = rememberNavController()
    var user: User? by remember { mutableStateOf(User()) }
    var selectedReference by remember { mutableStateOf<DocumentReference?>(null) }
    var gameId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            NavBar(navController)
        },

        bottomBar = {
            if (!user?.username.isNullOrBlank() || true) {
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
                LoginPage(navController)
            }
            composable(R.string.register_page_path.toString()) {
                RegisterPage(navController = navController)
            }
            composable(R.string.profile_page_path.toString()) {
                ProfilePage(navController)
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
                gameId = null
                MultiplayerPage(navController)
            }
            composable(R.string.quiz_page_path.toString()) {
                QuizPage(selectedReference)
            }
            //todo check if this is needed
//                composable(R.string.session_page_path.toString()) {
//                    SessionPage(gameId, user)
//                }
//                composable(R.string.join_page_path.toString()) {
//                    JoinPage {
//                        gameId = it
//                        navController.navigate(R.string.session_page_path.toString())
//                    }
//                }
        }
    }
}