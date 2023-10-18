package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.game.MultiplayerGame

interface GameService {
    suspend fun create(game: MultiplayerGame)
    suspend fun get(uuid: String): MultiplayerGame?
    suspend fun update(game: MultiplayerGame)
    suspend fun end(game: MultiplayerGame)
    suspend fun delete(uuid: String)
    suspend fun notifications(username: String): Int
}