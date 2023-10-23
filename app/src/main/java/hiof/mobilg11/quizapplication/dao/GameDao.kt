package hiof.mobilg11.quizapplication.dao

import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.User
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

    suspend fun getGamesNotifications(username: String): List<MultiplayerGame> {
        return firebase.collection(COLLECTION)
            .whereEqualTo("opponent", username)
            .whereEqualTo("gameState", GameState.REQUESTING_GAME)
            .get()
            .await()
            .toObjects(MultiplayerGame::class.java)
    }

    suspend fun getAllGamesByUsername(username: String): List<MultiplayerGame> {
        val hostedGames = firebase.collection(COLLECTION)
            .whereEqualTo("host", username)
            .get()
            .await()
            .toObjects(MultiplayerGame::class.java)
        val opponentGames = firebase.collection(COLLECTION)
            .whereEqualTo("opponent", username)
            .get()
            .await()
            .toObjects(MultiplayerGame::class.java)
        return hostedGames + opponentGames
    }

    suspend fun getAllActiveGamesByUsername(username: String): List<MultiplayerGame> {
        return getAllActiveGamesFilter(getAllGamesByUsername(username))
    }

    private fun getAllActiveGamesFilter(games: List<MultiplayerGame>): List<MultiplayerGame> {
        return games.filter { game ->
            game.gameState != GameState.FINISHED && game.gameState != GameState.REQUESTING_GAME
        }
    }

}