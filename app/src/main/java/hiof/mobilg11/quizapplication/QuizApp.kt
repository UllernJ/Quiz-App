package hiof.mobilg11.quizapplication

import MultiplayerPage
import NavBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hiof.mobilg11.quizapplication.ui.pages.QuizPage
import hiof.mobilg11.quizapplication.ui.navigation.BottomNavBar
import hiof.mobilg11.quizapplication.ui.pages.ProfilePage
import hiof.mobilg11.quizapplication.ui.pages.SinglePlayerPage
import hiof.mobilg11.quizapplication.ui.pages.auth.LoginPage
import hiof.mobilg11.quizapplication.ui.pages.auth.RegisterPage
import hiof.mobilg11.quizapplication.ui.pages.home.HomePage
import hiof.mobilg11.quizapplication.ui.pages.multiplayer.MultiplayerGameLobbyPage
import hiof.mobilg11.quizapplication.viewmodels.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizApp(viewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val user = viewModel.user.collectAsState()

    Scaffold(
//        topBar = {
//            NavBar(navController)
//        },
        bottomBar = {
            if (user.value != null) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (user.value == null) {
                Screen.Login.route
            } else {
                Screen.Home.route
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginPage(navController) {
                    viewModel.getUser()
                }
            }
            composable(Screen.Register.route) {
                RegisterPage(navController = navController)
            }
            composable(Screen.Profile.route) {
                ProfilePage {
                    viewModel.signOut()
                    navController.navigate(Screen.Login.route)
                }
            }
            composable(Screen.Home.route) {
                HomePage(navController = navController)
            }
            composable(Screen.SinglePlayer.route) {
                SinglePlayerPage {
                    val route = Screen.Quiz.createRoute(it)
                    navController.navigate(route)
                }
            }
            composable(Screen.Multiplayer.route) {
                MultiplayerPage(navController)
            }
            composable(
                route = Screen.MultiplayerLobby.route,
                arguments = listOf(
                    navArgument(LOBBY_ARGUMENT_KEY) {
                        nullable = false
                    })
            ) {
                MultiplayerGameLobbyPage()
            }
            composable(
                route = Screen.Quiz.route,
                arguments = listOf(
                    navArgument(QUIZ_ARGUMENT_KEY) {
                        nullable = false
                    })
            ) {
                QuizPage()
            }
        }
    }
}
