package hiof.mobilg11.quizapplication.ui.pages.multiplayer
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import hiof.mobilg11.quizapplication.dao.GameSessionDao
//import hiof.mobilg11.quizapplication.model.Game
//import hiof.mobilg11.quizapplication.model.UserSession
//import hiof.mobilg11.quizapplication.model.user.User
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class SessionViewModel : ViewModel() {
//    private val _game = MutableStateFlow(Game())
//    val currentGame: StateFlow<Game> = _game
//
//    var gameNumber = "Creating a game..."
//
//    fun createGame(user: User?) {
//        val newGame = Game(
//            players = mutableListOf(
//                UserSession(user)
//            ),
//            host = user,
//            categoriesPlayed = mutableListOf(),
//            questions = mutableListOf()
//        )
//
//        GameSessionDao().createGameSession(newGame) {
//            gameNumber = it.toString()
//        }
//        _game.value = newGame
//    }
//
//    fun joinGame(gameId: String, user: User?) {
//        GameSessionDao().getGameSession(gameId) { game ->
//            _game.value = game
//        }
//        gameNumber = gameId
//    }
//
//    fun startFetchingGameData() {
//        viewModelScope.launch {
//            while (true) {
//                GameSessionDao().getGameSession(gameNumber) { game ->
//                    _game.value = game
//                }
//                delay(2000L)
//            }
//        }
//    }
//}
