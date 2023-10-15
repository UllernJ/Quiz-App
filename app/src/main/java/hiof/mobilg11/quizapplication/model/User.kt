package hiof.mobilg11.quizapplication.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val uuid: String = "",
    var username: String = "",
    val winPercentage: Double? = 0.0,
//    val dateCreated: LocalDate? = null,
) {
}
