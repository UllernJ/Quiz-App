package hiof.mobilg11.quizapplication

import MultiplayerScreen
import NavBar
import android.media.MediaPlayer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.ui.navigation.BottomNavBar
import hiof.mobilg11.quizapplication.ui.screens.ProfileScreen
import hiof.mobilg11.quizapplication.ui.screens.auth.LoginScreen
import hiof.mobilg11.quizapplication.ui.screens.auth.RegisterScreen
import hiof.mobilg11.quizapplication.ui.screens.home.HomeScreen
import hiof.mobilg11.quizapplication.ui.screens.home.NotificationScreen
import hiof.mobilg11.quizapplication.ui.screens.leaderboard.LeaderboardScreen
import hiof.mobilg11.quizapplication.ui.screens.multiplayer.MultiplayerGameLobbyScreen
import hiof.mobilg11.quizapplication.ui.screens.multiplayer.MultiplayerPlayScreen
import hiof.mobilg11.quizapplication.ui.screens.singleplayer.QuizScreen
import hiof.mobilg11.quizapplication.ui.screens.singleplayer.SinglePlayerScreen
import hiof.mobilg11.quizapplication.ui.theme.BackgroundMusic
import hiof.mobilg11.quizapplication.ui.theme.NotificationSound
import hiof.mobilg11.quizapplication.utils.PlaySoundEffect
import hiof.mobilg11.quizapplication.viewmodels.auth.AuthViewModel
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
    val isNewNotification by appModel.isNewNotification.collectAsState(initial = false)
    var isFinishedLoaded by remember { mutableStateOf(false) }

    MusicPlayer()

    Scaffold(
        topBar = {
            if (currentRoute?.destination?.route == Screen.Register.route) {
                NavBar(navController = navController)
            }
        },
        bottomBar = {
            if (user.value != null
                && currentRoute?.destination?.route != Screen.Loading.route
                && currentRoute?.destination?.route != Screen.Quiz.route
                && currentRoute?.destination?.route != Screen.MultiplayerPlay.route
                && currentRoute?.destination?.route != Screen.Loading.route
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
                HomeScreen(
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
                MultiplayerScreen(user.value)
            }
            composable(
                route = Screen.MultiplayerLobby.route,
                arguments = listOf(
                    navArgument(LOBBY_ARGUMENT_KEY) {
                        nullable = false
                    })
            ) {
                MultiplayerGameLobbyScreen(navController, (user.value ?: User()))
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
                    isFinishedLoaded = true
                }
            }

            composable(
                route = Screen.MultiplayerPlay.route,
                arguments = listOf(navArgument(LOBBY_ARGUMENT_KEY) {
                    nullable = false
                })
            ) {
                MultiplayerPlayScreen(navController, user.value?.username ?: "")
            }
            composable(Screen.Notifications.route) {
                NotificationScreen(notifications = gameNotifications)
            }
            composable(Screen.Leaderboard.route) {
                LeaderboardScreen()
            }

        }
    }

    if (isNewNotification && isFinishedLoaded) {
        PlaySoundEffect(NotificationSound)
    }

}

@Composable
fun MusicPlayer() {
    val context = LocalContext.current
    val mediaPlayer = MediaPlayer.create(context, BackgroundMusic)
    mediaPlayer.isLooping = true
    mediaPlayer.start()

    DisposableEffect(mediaPlayer) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}

