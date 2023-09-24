package hiof.mobilg11.quizapplication.model

data class Category(
    val name: String,
    val description: String
) {
    fun getName(): String {
        return name
    }

    fun getDescription(): String {
        return description
    }

    override fun toString(): String {
        return name
    }
}
