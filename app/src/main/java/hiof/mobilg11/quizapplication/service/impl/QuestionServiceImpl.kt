package hiof.mobilg11.quizapplication.service.impl

import com.google.firebase.firestore.DocumentReference
import hiof.mobilg11.quizapplication.dao.QuestionDao
import hiof.mobilg11.quizapplication.service.QuestionService
import javax.inject.Inject

class QuestionServiceImpl @Inject constructor(private val questionDao: QuestionDao) :
    QuestionService {
    override suspend fun getQuestionsByCategoryReference(category: DocumentReference) =
        questionDao.getQuestionsByCategoryReference(category)
}