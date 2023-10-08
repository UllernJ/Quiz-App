package hiof.mobilg11.quizapplication.model

import hiof.mobilg11.quizapplication.model.user.User
import java.time.LocalDate

data class GameSession(
    val quiz: Quiz,
    val user: User,
    val score: Int,
    var isActive: Boolean,
    var dateStarted: LocalDate? = LocalDate.now(),
    var dateEnded: LocalDate? = null
) {
    fun start() {
        isActive = true
    }
    fun end() {
        isActive = false
        dateEnded = LocalDate.now()
    }
}

