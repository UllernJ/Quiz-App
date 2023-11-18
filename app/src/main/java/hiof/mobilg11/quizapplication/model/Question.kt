package hiof.mobilg11.quizapplication.model

data class Question(
    val question: String = "",
    var choices: List<String> = listOf(),
    val correctAnswer: String = "",
) {
}
