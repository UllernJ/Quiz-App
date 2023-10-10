package hiof.mobilg11.quizapplication.viewmodels;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hiof.mobilg11.quizapplication.dao.CategoryDao
import hiof.mobilg11.quizapplication.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SinglePlayerViewModel: ViewModel() {
    private val categoryDao = CategoryDao()

    private val _categories = MutableStateFlow(listOf<Category>())
    val categories: StateFlow<List<Category>> = _categories

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = categoryDao.getAll()
        }
    }

}
