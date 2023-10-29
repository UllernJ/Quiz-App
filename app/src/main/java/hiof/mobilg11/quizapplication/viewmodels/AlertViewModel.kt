package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val userService: UserService,
) : ViewModel() {

    fun setUsername(username: String) {
        viewModelScope.launch {
            userService.setUsername(username)
        }
    }

}