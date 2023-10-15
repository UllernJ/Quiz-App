package hiof.mobilg11.quizapplication.dao

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Question
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuestionDao @Inject constructor(private val firestore: FirebaseFirestore) {
    private val COLLECTION: String = "question"
    suspend fun getQuestionsByCategoryReference(documentReference: DocumentReference): MutableList<Question> =
        firestore.collection(COLLECTION)
            .whereEqualTo("category", documentReference)
            .get()
            .await()
            .toObjects(Question::class.java).toMutableList()
}

