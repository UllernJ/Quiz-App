package hiof.mobilg11.quizapplication.model

import hiof.mobilg11.quizapplication.model.user.User
import java.time.LocalDate

data class GameSession(
    val gameId: Int,
    val players: List<UserSession>,
    val categoriesPlayed: List<Category>,
    val questions: List<Question<*>>,
    val currentRound: Int = 0,
    val rounds: Int = 3,
    var status: GameSessionStatus = GameSessionStatus.PENDING,
    val dateCreated: LocalDate? = LocalDate.now(),
    val host: User? = null,
    val winner: User? = null
) {
    fun startGame() {
        status = GameSessionStatus.ACTIVE
    }
    fun endGame() {
        status = GameSessionStatus.FINISHED
    }

}

enum class GameSessionStatus {
    PENDING,
    ACTIVE,
    FINISHED
}

data class UserSession(
    val user: User,
    val state: UserState = UserState.NOT_READY
)

enum class UserState {
    READY,
    NOT_READY
}



