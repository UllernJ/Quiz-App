package hiof.mobilg11.quizapplication.service.impl

import hiof.mobilg11.quizapplication.dao.QuestionDao
import hiof.mobilg11.quizapplication.service.QuestionService
import javax.inject.Inject

class QuestionServiceImpl @Inject constructor(private val questionDao: QuestionDao) :
    QuestionService {
    override suspend fun getQuestionsByCategoryName(category: String) =
        questionDao.getQuestionsByCategoryName(category)
}