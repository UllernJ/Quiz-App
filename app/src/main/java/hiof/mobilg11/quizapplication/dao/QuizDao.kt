package hiof.mobilg11.quizapplication.dao

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.model.Quiz

class QuizDao(private val db: FirebaseFirestore) {
    //todo fix this to the new structure.
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
                    Log.d(ContentValues.TAG, "$questionRefs")

                    if (questionRefs != null && categoryRef != null) {
                        var category: Category? = null
                        var questions: MutableList<Question<*>> = mutableListOf()

                        getCategory(categoryRef) { fetchedCategory ->
                            category = fetchedCategory

                            if (category != null && questions.isNotEmpty()) {
                                val quiz = Quiz(questions, title, description, category!!)
                                quizList.add(quiz)
                                callback(quizList)
                            }
                        }

                        getQuestions(questionRefs) { question ->
                            questions = question

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
                             callback: (MutableList<Question<*>>) -> Unit) {
        val questions = mutableListOf<Question<*>>()
        for (questionReference in questionReferences) {
            questionReference.get()
                .addOnSuccessListener { document ->
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    if(document.data !== null) {
                        val question = Question(
                            question = document.data?.get("question") as String,
                            choices = document.data?.get("choices") as List<*>,
                            correctAnswer = document.data?.get("correctAnswer") as String
                        )
                        questions.add(question)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
        }
        callback(questions)
    }

}