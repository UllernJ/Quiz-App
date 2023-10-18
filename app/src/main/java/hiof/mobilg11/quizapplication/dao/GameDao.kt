package hiof.mobilg11.quizapplication.dao

import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GameDao @Inject constructor(private val firebase: FirebaseFirestore) {
    private val COLLECTION = "games"
    suspend fun createGame(game: MultiplayerGame) {
        firebase.collection(COLLECTION)
            .document(game.uuid)
            .set(game)
            .await()
    }

    suspend fun getGame(uuid: String): MultiplayerGame? {
        return firebase.collection(COLLECTION)
            .document(uuid)
            .get()
            .await()
            .toObject(MultiplayerGame::class.java)
    }

    suspend fun updateGame(game: MultiplayerGame) {
        firebase.collection(COLLECTION)
            .document(game.uuid)
            .set(game)
            .await()
    }

    suspend fun endGame(game: MultiplayerGame) {
        game.gameState = GameState.FINISHED

        if (game.hostScore > game.opponentScore) {
            game.winner = game.host
        } else if (game.hostScore < game.opponentScore) {
            game.winner = game.opponent
        }

        firebase.collection(COLLECTION)
            .document(game.uuid)
            .set(game)
            .await()
    }

    suspend fun deleteGame(game: MultiplayerGame) {
        firebase.collection(COLLECTION)
            .document(game.uuid)
            .delete()
            .await()
    }

    suspend fun getGamesNotifications(username: String): Int {
        return firebase.collection(COLLECTION)
            .whereEqualTo("opponent", username)
            .whereEqualTo("gameState", GameState.REQUESTING_GAME)
            .get()
            .await()
            .toObjects(MultiplayerGame::class.java)
            .size
    }
}