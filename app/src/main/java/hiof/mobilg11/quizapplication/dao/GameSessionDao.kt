package hiof.mobilg11.quizapplication.dao
//
//import android.util.Log
//import com.google.firebase.firestore.FirebaseFirestore
//import hiof.mobilg11.quizapplication.model.Game
//import hiof.mobilg11.quizapplication.model.UserSession
//import hiof.mobilg11.quizapplication.model.UserState
//import hiof.mobilg11.quizapplication.model.user.User
//
//class GameSessionDao {
//    private val db = FirebaseFirestore.getInstance()
//    private val COLLECTION = "gameSessions"
//
//    fun createGameSession(game: Game, callbackGameId: (Int) -> Unit) {
//        val gameId = (0..100000).random()
//        db.collection(COLLECTION)
//            .document(gameId.toString())
//            .set(game)
//            .addOnSuccessListener {
//                callbackGameId(gameId)
//            }
//    }
//
//    fun getGameSession(gameId: String, callbackGameSession: (Game) -> Unit) {
//        db.collection(COLLECTION)
//            .document(gameId)
//            .get()
//            .addOnSuccessListener {
//                Log.d("GameSessionDao", "Successfully retrieved game session")
//                val game = it.toObject(Game::class.java)
//                if (game != null) {
//                    callbackGameSession(game)
//                }
//            }
//    }
//
//    fun joinSession(gameId: String, callback: (Boolean) -> Unit) {
//        UserDao().getUser { user ->
//            getGameSession(gameId) {
//                val gameSession = it
//                val player = UserSession(user = user)
//                if(!gameSession.players.contains(player) && gameSession.host != user) {
//                    gameSession.players.add(player)
//                    db.collection(COLLECTION)
//                        .document(gameId)
//                        .update("players", gameSession.players)
//                        .addOnSuccessListener {
//                            callback(true)
//                            Log.d("GameSessionDao", "Successfully joined session")
//                        }
//                        .addOnFailureListener {
//                            callback(false)
//                            Log.d("GameSessionDao", "Failed to join session")
//                        }
//                } else {
//                    callback(true)
//                    Log.d("GameSessionDao", "Player already in session")
//                }
//            }
//        }
//    }
//    fun readyUp(gameId: String) {
//        UserDao().getUser { user ->
//            getGameSession(gameId) {
//                val gameSession = it
//                val player = UserSession(user = user)
//                if(gameSession.players.contains(player)) {
//                    gameSession.players[gameSession.players.indexOf(player)].state = UserState.READY
//                    db.collection(COLLECTION)
//                        .document(gameId)
//                        .update("players", gameSession.players)
//                        .addOnSuccessListener {
//                            Log.d("GameSessionDao", "Successfully readied up")
//                        }
//                        .addOnFailureListener {
//                            Log.d("GameSessionDao", "Failed to ready up")
//                        }
//                } else {
//                    Log.d("GameSessionDao", "Player not in session")
//                }
//            }
//        }
//    }
//}