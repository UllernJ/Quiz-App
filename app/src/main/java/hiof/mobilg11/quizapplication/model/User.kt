package hiof.mobilg11.quizapplication.model

import java.time.LocalDate

data class User(
    val username: String,
    val winPercentage: Int,
    val dateCreated: LocalDate? = LocalDate.now()
)
