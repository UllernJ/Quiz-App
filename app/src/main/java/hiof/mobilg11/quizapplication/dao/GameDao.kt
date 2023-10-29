package hiof.mobilg11.quizapplication.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import hiof.mobilg11.quizapplication.model.game.GameState
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GameDao @Inject constructor(
    private val firebase: FirebaseFirestore
) {
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

    fun getGamesNotifications(username: String): Flow<List<MultiplayerGame>> {
        return firebase.collection(COLLECTION)
            .whereEqualTo("opponent", username)
            .whereEqualTo("gameState", GameState.REQUESTING_GAME)
            .dataObjects()
    }

    private fun getAllGamesByUsername(username: String): Flow<List<MultiplayerGame>> {
        val hostedGames = firebase.collection(COLLECTION)
            .whereEqualTo("host", username)
            .dataObjects<MultiplayerGame>()
        val opponentGames = firebase.collection(COLLECTION)
            .whereEqualTo("opponent", username)
            .dataObjects<MultiplayerGame>()

        return hostedGames.combine(opponentGames) { hosted, opponent ->
            getAllActiveGamesFilter(hosted) + getAllActiveGamesFilter(opponent)
        }
    }

    fun getAllActiveGamesByUsername(username: String): Flow<List<MultiplayerGame>> {
        return getAllGamesByUsername(username)
    }

    private fun getAllActiveGamesFilter(games: List<MultiplayerGame>): List<MultiplayerGame> {
        return games.filter { game ->
            game.gameState != GameState.FINISHED && game.gameState != GameState.REQUESTING_GAME
        }
    }

    private companion object {
        private const val COLLECTION = "games"
    }

}