package hiof.mobilg11.quizapplication.dao

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.model.Quiz

class QuizDao(private val db: FirebaseFirestore) {
    fun getAllQuiz(callback: (List<Quiz>) -> Unit) {
        db.collection("quiz")
            .get()
            .addOnSuccessListener { result ->
                val quizList = mutableListOf<Quiz>()

                for (document in result) {
                    val title = document.data["title"] as String
                    val description = document.data["description"] as String
                    val categoryRef = document.getDocumentReference("category")
                    val questionRefs = document.get("questions") as? List<DocumentReference>

                    if (questionRefs != null && categoryRef != null) {
                        var category: Category? = null
                        val questions: MutableList<Question<*>> = mutableListOf()

                        getCategory(categoryRef) { fetchedCategory ->
                            category = fetchedCategory

                            if (category != null && questions.isNotEmpty()) {
                                val quiz = Quiz(questions, title, description, category!!)
                                quizList.add(quiz)
                                callback(quizList)
                            }
                        }

                        getQuestions(questionRefs) { question ->
                            questions.add(question)

                            if (category != null && questions.isNotEmpty()) {
                                val quiz = Quiz(questions, title, description, category!!)
                                quizList.add(quiz)
                                callback(quizList)
                            }
                        }
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getCategory(categoryReference: DocumentReference,
                            callback: (Category?) -> Unit) {
        categoryReference.get()
            .addOnSuccessListener { document ->
                val name = document.data?.get("name") as String
                val description = document.data?.get("description") as String
                val category = Category(name, description)
                callback(category)
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }

    private fun getQuestions(questionReferences: List<DocumentReference>,
                             callback: (Question<*>) -> Unit) {
        for (questionReference in questionReferences) {
            questionReference.get()
                .addOnSuccessListener { document ->
                    val question = Question(
                        question = document.data?.get("question") as String,
                        choices = document.data?.get("choices") as List<String>,
                        correctAnswer = document.data?.get("correctAnswer") as String
                    )
                    callback(question)
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
        }
    }

}