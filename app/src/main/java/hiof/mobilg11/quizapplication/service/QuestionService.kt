package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.Question

interface QuestionService {
    suspend fun getQuestionsByCategoryName(categoryName: String): List<Question>
}