package hiof.mobilg11.quizapplication.model.user

import java.time.LocalDate

data class User(
    val uuid: String = "",
    val username: String = "",
    val winPercentage: Double? = 0.0,
    val dateCreated: LocalDate? = null,
    val friendList: List<Friend> = listOf()
) {
}
