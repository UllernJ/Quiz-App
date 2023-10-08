package hiof.mobilg11.quizapplication.dao

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Question

class QuestionDao(private val db: FirebaseFirestore) {
    fun getQuestionsByCategoryReference(documentReference: DocumentReference, callback: (List<Question>) -> Unit) {
        val questionList = mutableListOf<Question>()
        db.collection("question")
            .whereEqualTo("category", documentReference)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val question = document.toObject(Question::class.java)
                    questionList.add(question)
                }
                callback(questionList)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

    }
}

