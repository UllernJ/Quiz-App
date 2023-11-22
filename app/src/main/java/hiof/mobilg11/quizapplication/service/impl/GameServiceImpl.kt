package hiof.mobilg11.quizapplication.service.impl

import hiof.mobilg11.quizapplication.dao.GameDao
import hiof.mobilg11.quizapplication.model.PlayerStats
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameServiceImpl @Inject constructor(private val gameDao: GameDao) : GameService {

    override fun notifications(username: String) = gameDao.getGamesNotifications(username)

    override suspend fun create(game: MultiplayerGame) {
        if (game.host.isBlank() || game.opponent.isBlank() || game.host == game.opponent) {
            return
        } else {
            gameDao.createGame(game)
        }
    }

    override suspend fun get(uuid: String) = gameDao.getGame(uuid)

    override suspend fun update(game: MultiplayerGame) {
        gameDao.updateGame(game)
    }

    override suspend fun end(game: MultiplayerGame) {
        gameDao.endGame(game)
    }

    override suspend fun delete(uuid: String) {
        gameDao.deleteGame(MultiplayerGame(uuid))
    }

    override fun getGames(username: String): Flow<List<MultiplayerGame>> {
        return gameDao.getAllActiveGamesByUsername(username)
    }

    override suspend fun getGameStatistics(username: String): List<MultiplayerGame> {
        return gameDao.getGameStatistics(username)
    }

    override suspend fun getPlayerStats(username: String): PlayerStats {
        val games = gameDao.getGameStatistics(username)
        if (games.isEmpty()) {
            return PlayerStats(username, 0, 0, 0, 0)
        }
        var wins = 0
        var losses = 0
        var draws = 0
        for (game in games) {
            if (game.winner == username) {
                wins++
            } else if (game.winner == "DRAW" || game.winner == null) {
                draws++
            } else {
                losses++
            }
        }

        return PlayerStats(username, games.size, wins, losses, draws)
    }

    override suspend fun getRecentlyPlayedAgainst(username: String): List<String> {
        val games = gameDao.getGameStatistics(username)
        val opponents = mutableListOf<String>()
        games.forEach { game ->
            opponents.add(game.host)
            opponents.add(game.opponent)
        }
        return opponents.distinct().filter { it != username }
    }

    override suspend fun getLeaderboardStatistics(): List<PlayerStats> {
        val games = gameDao.getAllGames()
        if (games.isNotEmpty()) {
            return calculateAllPlayerStats(games)
        }
        return listOf()
    }

    private fun calculateAllPlayerStats(list: List<MultiplayerGame>): List<PlayerStats> {
        val playerStatsMap = mutableMapOf<String, PlayerStats>()
        list.forEach { game ->
            if(!playerStatsMap.containsKey(game.host)) {
                playerStatsMap[game.host] = PlayerStats(game.host, 0, 0, 0, 0)
            }
            if(!playerStatsMap.containsKey(game.opponent)) {
                playerStatsMap[game.opponent] = PlayerStats(game.opponent, 0, 0, 0, 0)
            }
            if(game.winner == game.host) {
                playerStatsMap[game.host]?.gamesWon = playerStatsMap[game.host]?.gamesWon?.plus(1) ?: 1
                playerStatsMap[game.opponent]?.gamesLost = playerStatsMap[game.opponent]?.gamesLost?.plus(1) ?: 1
            } else if(game.winner == game.opponent) {
                playerStatsMap[game.opponent]?.gamesWon = playerStatsMap[game.opponent]?.gamesWon?.plus(1) ?: 1
                playerStatsMap[game.host]?.gamesLost = playerStatsMap[game.host]?.gamesLost?.plus(1) ?: 1
            } else {
                playerStatsMap[game.host]?.gamesDraw = playerStatsMap[game.host]?.gamesDraw?.plus(1) ?: 1
                playerStatsMap[game.opponent]?.gamesDraw = playerStatsMap[game.opponent]?.gamesDraw?.plus(1) ?: 1
            }
        }
        val playerStatsList = playerStatsMap.values.toList()
        playerStatsList.forEach { playerStats ->
            playerStats.gamesPlayed = playerStats.gamesWon + playerStats.gamesLost + playerStats.gamesDraw
        }
        return playerStatsList.sortedByDescending { it.gamesWon }
    }


}