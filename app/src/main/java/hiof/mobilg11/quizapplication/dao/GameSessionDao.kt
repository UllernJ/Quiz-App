package hiof.mobilg11.quizapplication.dao

import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.GameSession

class GameSessionDao {
    private val db = FirebaseFirestore.getInstance()

    fun createGameSession(gameSession: GameSession, callbackGameId: (Int) -> Unit) {
        val gameId = (0..100000).random()
        db.collection("gameSessions")
            .document(gameId.toString())
            .set(gameSession)
            .addOnSuccessListener {
                callbackGameId(gameId)
            }
    }
}