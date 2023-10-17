package hiof.mobilg11.quizapplication

const val QUIZ_ARGUMENT_KEY = "categoryName"

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Profile : Screen("profile")
    data object Quiz : Screen("quiz/{$QUIZ_ARGUMENT_KEY}") {
        fun createRoute(categoryName: String): String {
            return "quiz/$categoryName"
        }
    }
    data object Multiplayer : Screen("multiplayer")
    data object SinglePlayer : Screen("singleplayer")
}
