package hiof.mobilg11.quizapplication.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

data class Category(
    val name: String = "",
    @DocumentId val documentReference: DocumentReference? = null
) {
}
