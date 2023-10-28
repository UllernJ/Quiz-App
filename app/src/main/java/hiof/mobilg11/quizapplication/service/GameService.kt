package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import kotlinx.coroutines.flow.Flow

interface GameService {
    val notifications: Flow<List<MultiplayerGame>>
    suspend fun create(game: MultiplayerGame)
    suspend fun get(uuid: String): MultiplayerGame?
    suspend fun update(game: MultiplayerGame)
    suspend fun end(game: MultiplayerGame)
    suspend fun delete(uuid: String)
    suspend fun getGames(username: String): List<MultiplayerGame>
}