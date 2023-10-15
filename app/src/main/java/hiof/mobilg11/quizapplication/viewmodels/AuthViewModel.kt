package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.service.AuthService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userCache: UserCacheService,
    private val accountService: AuthService,
    private val userService: UserService
) : ViewModel() {

    private var user: User? = null

    init {
        user = userCache.getUser()
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onLoginResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val result = accountService.signInWithEmailAndPassword(email, password)
            if (result) {
                saveUser()
                user = userService.get()
                onLoginResult(true)
            } else {
                onLoginResult(false)
            }
        }
    }

    private fun saveUser() {
        viewModelScope.launch {
            val user = userService.get()
            if (user != null) {
                userCache.saveUser(user)
            }
        }
    }

    fun getUser(): User? {
        return user
    }

    fun signOut() {
        userCache.clearUser()
        viewModelScope.launch {
            accountService.signOut()
        }
    }

}