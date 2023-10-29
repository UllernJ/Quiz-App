package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.AuthService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService
) : ViewModel() {

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onLoginResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val result = authService.signInWithEmailAndPassword(email, password)
            if (result) {
                onLoginResult(true)
            } else {
                onLoginResult(false)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }

}