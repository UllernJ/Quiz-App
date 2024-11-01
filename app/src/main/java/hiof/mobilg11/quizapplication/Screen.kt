package hiof.mobilg11.quizapplication

/*
Inspired by : https://stackoverflow.com/questions/69686087/why-use-sealed-class-and-make-object-in-navigation-kotlin-jetpack-compose
 */


const val QUIZ_ARGUMENT_KEY = "categoryName"
const val LOBBY_ARGUMENT_KEY = "lobbyId"

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Profile : Screen("profile")
    data object Quiz : Screen("quiz/{$QUIZ_ARGUMENT_KEY}") {
        fun createRoute(categoryName: String): String {
            return this.route.replace("{$QUIZ_ARGUMENT_KEY}", categoryName)
        }
    }

    data object Multiplayer : Screen("multiplayer")
    data object MultiplayerLobby : Screen("multiplayer/{$LOBBY_ARGUMENT_KEY}") {
        fun createRoute(lobbyId: String): String {
            return this.route.replace("{$LOBBY_ARGUMENT_KEY}", lobbyId)
        }
    }

    data object SinglePlayer : Screen("singleplayer")
    data object Loading : Screen("loading")
    data object Notifications : Screen("notifications")
    data object Leaderboard : Screen("leaderboard")
}
