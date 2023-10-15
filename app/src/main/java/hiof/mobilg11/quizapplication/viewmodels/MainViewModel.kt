package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private var _isSet = MutableStateFlow(true)
    var isSet: StateFlow<Boolean> = _isSet

    init {
        viewModelScope.launch {
            isUsernameSet()
        }
    }

    suspend fun setUsername(username: String): Boolean {
        return userService.setUsername(username)
    }

    suspend fun createUser() {
        userService.create()
    }

    suspend fun isUsernameSet() {
        viewModelScope.launch {
            _isSet.value = userService.isUsernameSet()
        }
    }

    fun setIsSet(value: Boolean) {
        _isSet.value = value
    }
}