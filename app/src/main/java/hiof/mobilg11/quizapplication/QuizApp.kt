package hiof.mobilg11.quizapplication

import MultiplayerPage
import NavBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.DocumentReference
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.ui.QuizPage
import hiof.mobilg11.quizapplication.ui.navigation.BottomNavBar
import hiof.mobilg11.quizapplication.ui.pages.ProfilePage
import hiof.mobilg11.quizapplication.ui.pages.SinglePlayerPage
import hiof.mobilg11.quizapplication.ui.pages.auth.LoginPage
import hiof.mobilg11.quizapplication.ui.pages.auth.RegisterPage
import hiof.mobilg11.quizapplication.ui.pages.home.HomePage
import hiof.mobilg11.quizapplication.viewmodels.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizApp(viewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    var user: User? by remember { mutableStateOf(null) }
    var selectedReference by remember { mutableStateOf<DocumentReference?>(null) }
    val fetchedUser = viewModel.getUser()
    if (fetchedUser != null) {
        user = fetchedUser
    }

    Scaffold(
        topBar = {
            NavBar(navController)
        },
        bottomBar = {
            if (user != null) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (user == null) {
                R.string.login_page_path.toString()
            } else {
                R.string.home_page_path.toString()
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(R.string.login_page_path.toString()) {
                LoginPage(navController) {
                    user = it
                }
            }
            composable(R.string.register_page_path.toString()) {
                RegisterPage(navController = navController)
            }
            composable(R.string.profile_page_path.toString()) {
                ProfilePage(navController) {
                    user = null
                }
            }
            composable(R.string.home_page_path.toString()) {
                HomePage(navController = navController)
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
