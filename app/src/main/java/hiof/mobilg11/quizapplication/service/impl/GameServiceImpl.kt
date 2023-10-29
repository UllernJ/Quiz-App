package hiof.mobilg11.quizapplication.service.impl

import android.widget.Toast
import hiof.mobilg11.quizapplication.dao.GameDao
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.service.GameService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameServiceImpl @Inject constructor(private val gameDao: GameDao) : GameService {

    override val notifications = gameDao.notifications

    override suspend fun create(game: MultiplayerGame) {
        if (game.host == game.opponent) {
            Toast.makeText(
                null,
                "You cannot challenge yourself",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if (game.host.isBlank() || game.opponent.isBlank()) {
            Toast.makeText(
                null,
                "You cannot challenge someone with a blank username",
                Toast.LENGTH_SHORT
            ).show()
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


}