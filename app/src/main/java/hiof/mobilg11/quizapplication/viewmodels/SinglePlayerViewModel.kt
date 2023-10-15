package hiof.mobilg11.quizapplication.viewmodels;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.service.CategoryService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SinglePlayerViewModel @Inject constructor(private val categoryService: CategoryService): ViewModel() {
    private val _categories = MutableStateFlow(listOf<Category>())
    val categories: StateFlow<List<Category>> = _categories

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            delay(1500L)
            _categories.value = categoryService.getAllCategories()
        }
    }

}
