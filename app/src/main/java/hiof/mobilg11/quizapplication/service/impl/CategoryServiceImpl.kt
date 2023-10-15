package hiof.mobilg11.quizapplication.service.impl

import hiof.mobilg11.quizapplication.dao.CategoryDao
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.service.CategoryService
import javax.inject.Inject

class CategoryServiceImpl @Inject constructor(private val categoryDao: CategoryDao):
    CategoryService {
    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAll()
    }
}