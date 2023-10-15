package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val userService: UserService,
    private val userCacheService: UserCacheService
) : ViewModel() {

    private val _isSet = MutableStateFlow(false)
    val isSet: StateFlow<Boolean> = _isSet

    fun setUsername(username: String) {
        viewModelScope.launch {
            _isSet.value = userService.setUsername(username)
            if (_isSet.value) {
                val user: User? = userService.get()
                user?.username = username
                if (user != null) {
                    userCacheService.updateUser(user)
                }
            }
        }
    }

}