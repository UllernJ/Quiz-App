package hiof.mobilg11.quizapplication.dao

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Question
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuestionDao @Inject constructor(private val firestore: FirebaseFirestore) {
    private val COLLECTION: String = "question"
    suspend fun getQuestionsByCategoryName(name: String): MutableList<Question> {
        val reference = getDocumentReferenceByCategoryName(name)
        return firestore.collection(COLLECTION)
            .whereEqualTo("category", reference)
            .get()
            .await()
            .toObjects(Question::class.java)
    }


    private suspend fun getDocumentReferenceByCategoryName(categoryName: String): DocumentReference  = firestore.collection("category")
            .whereEqualTo("name", categoryName)
            .get()
            .await()
            .documents[0]
            .reference

}

