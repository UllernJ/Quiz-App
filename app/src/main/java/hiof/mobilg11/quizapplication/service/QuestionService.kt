package hiof.mobilg11.quizapplication.service

import com.google.firebase.firestore.DocumentReference
import hiof.mobilg11.quizapplication.model.Question

interface QuestionService {
    suspend fun getQuestionsByCategoryReference(category: DocumentReference): List<Question>
}