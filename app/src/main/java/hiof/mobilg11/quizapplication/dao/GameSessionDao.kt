package hiof.mobilg11.quizapplication.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Game
import hiof.mobilg11.quizapplication.model.UserSession
import hiof.mobilg11.quizapplication.model.user.User

class GameSessionDao {
    private val db = FirebaseFirestore.getInstance()
    private val COLLECTION = "gameSessions"

    fun createGameSession(game: Game, callbackGameId: (Int) -> Unit) {
        val gameId = (0..100000).random()
        db.collection(COLLECTION)
            .document(gameId.toString())
            .set(game)
            .addOnSuccessListener {
                callbackGameId(gameId)
            }
    }

    fun getGameSession(gameId: String, callbackGameSession: (Game) -> Unit) {
        db.collection(COLLECTION)
            .document(gameId)
            .get()
            .addOnSuccessListener {
                val game = it.toObject(Game::class.java)
                if (game != null) {
                    callbackGameSession(game)
                }
            }
    }

    fun joinSession(gameId: String) {
        var user: User? = null
        UserDao().getUser { user ->
            getGameSession(gameId) {
                val gameSession = it
                val player = UserSession(user = user)
                if(!gameSession.players.contains(player)) {
                    gameSession.players.add(player)
                    db.collection(COLLECTION)
                        .document(gameId)
                        .update("players", gameSession.players)
                        .addOnSuccessListener {
                            Log.d("GameSessionDao", "Successfully joined session")
                        }
                        .addOnFailureListener {
                            Log.d("GameSessionDao", "Failed to join session")
                        }
                } else {
                    Log.d("GameSessionDao", "Player already in session")
                }
            }
        }
    }
}