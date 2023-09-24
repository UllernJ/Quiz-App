package hiof.mobilg11.quizapplication.model

data class Question<T>(
    val question: String,
    val choices: List<T>,
    val correctAnswer: T
) {
}
