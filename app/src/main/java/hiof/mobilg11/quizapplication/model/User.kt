package hiof.mobilg11.quizapplication.model

import java.time.LocalDate

data class User(
    val uuid: String,
    val username: String,
    val winPercentage: Int,
    val dateCreated: LocalDate? = LocalDate.now()
) {
    fun getUuid(): String {
        return uuid
    }

    fun getUsername(): String {
        return username
    }

    fun getWinPercentage(): Int {
        return winPercentage
    }

    fun getDateCreated(): LocalDate? {
        return dateCreated
    }
}
