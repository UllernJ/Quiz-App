package hiof.mobilg11.quizapplication

import MultiplayerScreen
import NavBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hiof.mobilg11.quizapplication.ui.navigation.BottomNavBar
import hiof.mobilg11.quizapplication.ui.screen.ProfileScreen
import hiof.mobilg11.quizapplication.ui.screen.QuizScreen
import hiof.mobilg11.quizapplication.ui.screen.SinglePlayerScreen
import hiof.mobilg11.quizapplication.ui.screen.auth.LoginScreen
import hiof.mobilg11.quizapplication.ui.screen.auth.RegisterScreen
import hiof.mobilg11.quizapplication.ui.screen.multiplayer.MultiplayerGameLobbyScreen
import hiof.mobilg11.quizapplication.ui.screen.multiplayer.MultiplayerPlayScreen
import hiof.mobilg11.quizapplication.ui.screen.play.NotificationScreen
import hiof.mobilg11.quizapplication.ui.screen.play.PlayScreen
import hiof.mobilg11.quizapplication.viewmodels.AuthViewModel
import hiof.mobilg11.quizapplication.viewmodels.QuizAppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizApp(
    auth: AuthViewModel = hiltViewModel(),
    appModel: QuizAppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val user = appModel.user.collectAsState()
    val currentRoute by navController.currentBackStackEntryAsState()
    val gameNotifications by appModel.notifications.collectAsState(initial = emptyList())


    Scaffold(
        topBar = {
            if (currentRoute?.destination?.route == Screen.Quiz.route
                && currentRoute?.destination?.route != Screen.MultiplayerPlay.route
            ) {
                NavBar(navController)
            }
        },
        bottomBar = {
            if (user.value != null
                && currentRoute?.destination?.route != Screen.Loading.route
                && currentRoute?.destination?.route != Screen.Quiz.route
                && currentRoute?.destination?.route != Screen.MultiplayerPlay.route
            ) {
                BottomNavBar(navController, gameNotifications.size)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Loading.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(navController) {
                    appModel.startUserFlow()
                }
            }
            composable(Screen.Register.route) {
                RegisterScreen(navController = navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(user.value) {
                    auth.signOut()
                    appModel.stopFlow()
                    navController.navigate(Screen.Login.route)
                }
            }
            composable(Screen.Home.route) {
                PlayScreen(
                    navController = navController,
                    gameNotifications = gameNotifications,
                    user = user.value
                )
            }
            composable(Screen.SinglePlayer.route) {
                SinglePlayerScreen {
                    val route = Screen.Quiz.createRoute(it)
                    navController.navigate(route)
                }
            }
            composable(Screen.Multiplayer.route) {
                MultiplayerScreen(navController)
            }
            composable(
                route = Screen.MultiplayerLobby.route,
                arguments = listOf(
                    navArgument(LOBBY_ARGUMENT_KEY) {
                        nullable = false
                    })
            ) {
                MultiplayerGameLobbyScreen(navController)
            }
            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument(QUIZ_ARGUMENT_KEY) {
                    nullable = false
                })
            ) {
                QuizScreen(navController)
            }
            composable(Screen.Loading.route) {
                LoadingScreen {
                    if (user.value != null) {
                        navController.navigate(Screen.Home.route)
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                }
            }

            composable(
                route = Screen.MultiplayerPlay.route,
                arguments = listOf(navArgument(LOBBY_ARGUMENT_KEY) {
                    nullable = false
                })
            ) {
                MultiplayerPlayScreen(navController)
            }
            composable(Screen.Notifications.route) {
                NotificationScreen()
            }

        }
    }
}
