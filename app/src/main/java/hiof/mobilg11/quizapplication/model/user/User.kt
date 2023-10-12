package hiof.mobilg11.quizapplication.model.user

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val uuid: String = "",
    val username: String = "",
    val winPercentage: Double? = 0.0,
    val friendList: List<Friend> = listOf()
//    val dateCreated: LocalDate? = null,
) {
}
