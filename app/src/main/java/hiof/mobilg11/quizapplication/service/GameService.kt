package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import kotlinx.coroutines.flow.Flow

interface GameService {
    fun notifications(username: String): Flow<List<MultiplayerGame>>
    suspend fun create(game: MultiplayerGame)
    suspend fun get(uuid: String): MultiplayerGame?
    suspend fun update(game: MultiplayerGame)
    suspend fun end(game: MultiplayerGame)
    suspend fun delete(uuid: String)
    fun getGames(username: String): Flow<List<MultiplayerGame>>
    suspend fun getGameStatistics(username: String): List<MultiplayerGame>
    suspend fun getWinPercentage(username: String): Double
}