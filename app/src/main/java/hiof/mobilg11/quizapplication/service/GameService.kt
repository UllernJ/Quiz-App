package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.PlayerStats
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import kotlinx.coroutines.flow.Flow

interface GameService {
    fun notifications(username: String): Flow<List<MultiplayerGame>>
    suspend fun create(game: MultiplayerGame): Boolean
    suspend fun get(uuid: String): MultiplayerGame?
    suspend fun update(game: MultiplayerGame)
    suspend fun end(game: MultiplayerGame)
    suspend fun delete(uuid: String)
    fun getGames(username: String): Flow<List<MultiplayerGame>>
    suspend fun getGameStatistics(username: String): List<MultiplayerGame>
    suspend fun getPlayerStats(username: String): PlayerStats
    suspend fun getRecentlyPlayedAgainst(username: String): List<String>
    suspend fun getLeaderboardStatistics(): List<PlayerStats>
}