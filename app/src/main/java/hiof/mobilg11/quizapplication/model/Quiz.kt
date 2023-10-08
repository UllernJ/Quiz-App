package hiof.mobilg11.quizapplication.model

import java.time.LocalDate

data class Quiz (
    val questions: List<Question>,
    val title: String,
    val description: String,
    val category: Category,
    val dateCreated: LocalDate? = LocalDate.now()
    ) {
}