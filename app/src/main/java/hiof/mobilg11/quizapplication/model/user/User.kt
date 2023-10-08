package hiof.mobilg11.quizapplication.model.user

import java.time.LocalDate

data class User(
    val uuid: String,
    val username: String,
    val winPercentage: Double,
    val dateCreated: LocalDate? = LocalDate.now(),
    val friendList: List<Friend>
) {
}
