package hiof.mobilg11.quizapplication.model

data class Question<T>(
    val question: String,
    val choices: List<T>,
    val correctAnswer: T
) {
    fun isCorrectAnswer(answer: T): Boolean {
        return answer == correctAnswer
    }

    fun getCorrectAnswer(): T {
        return correctAnswer
    }

    fun getChoices(): List<T> {
        return choices
    }

    fun getQuestion(): String {
        return question
    }

}
