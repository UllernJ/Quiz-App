package hiof.mobilg11.quizapplication.service.impl

import hiof.mobilg11.quizapplication.dao.GameDao
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import javax.inject.Inject

class GameServiceImpl @Inject constructor(private val gameDao: GameDao) : GameService {

    override val notifications = gameDao.notifications

    override suspend fun create(game: MultiplayerGame) {
        if (game.host == game.opponent) {
            throw IllegalArgumentException("Host and opponent cannot be the same")
        }
        if (game.host.isBlank() || game.opponent.isBlank()) {
            throw IllegalArgumentException("Host and opponent cannot be blank")
        }
        gameDao.createGame(game)
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

    override suspend fun getGames(username: String): List<MultiplayerGame> {
        return gameDao.getAllActiveGamesByUsername(username)
    }


}