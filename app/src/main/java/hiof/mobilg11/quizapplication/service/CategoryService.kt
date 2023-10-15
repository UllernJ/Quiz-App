package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.Category

interface CategoryService {
    suspend fun getAllCategories(): List<Category>
}