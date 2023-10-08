package hiof.mobilg11.quizapplication.model

data class Question(
    val question: String = "",
    val choices: List<String> = listOf(),
    val correctAnswer: String = "",
) {
}
