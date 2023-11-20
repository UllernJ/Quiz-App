package hiof.mobilg11.quizapplication.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val uuid: String = "",
    var username: String = "",
) {
}
