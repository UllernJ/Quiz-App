package hiof.mobilg11.quizapplication.model

import hiof.mobilg11.quizapplication.model.user.User

data class Game(
    val players: MutableList<UserSession> = mutableListOf(),
    val categoriesPlayed: MutableList<Category> = mutableListOf(),
    val questions: MutableList<Question<*>> = mutableListOf(),
    val currentRound: Int = 0,
    val rounds: Int = 3,
    var status: GameSessionStatus = GameSessionStatus.PENDING,
    val host: User? = null,
    val winner: User? = null
) {
    fun startGame() {
        status = GameSessionStatus.ACTIVE
    }
    fun endGame() {
        status = GameSessionStatus.FINISHED
    }
    fun addPlayer(user: User) {
        val player = UserSession(
            user = user
        )
        players.add(player)
    }

}

enum class GameSessionStatus {
    PENDING,
    ACTIVE,
    FINISHED
}

data class UserSession(
    val user: User? = null,
    val state: UserState = UserState.NOT_READY
)

enum class UserState {
    READY,
    NOT_READY
}



