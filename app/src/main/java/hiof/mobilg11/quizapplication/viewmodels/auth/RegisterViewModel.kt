package hiof.mobilg11.quizapplication.viewmodels.auth

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.model.Error
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userService: UserService,
    private val auth: FirebaseAuth,
) : ViewModel() {

    fun createUserWithEmailAndPassword(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
        onRegisterResult: (Error?) -> Unit
    ) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            onRegisterResult(Error.EMPTY_FIELDS)
            return
        }
        if (username.length < 3) {
            onRegisterResult(Error.USERNAME_TOO_SHORT)
            return
        }
        if (username.length > 12) {
            onRegisterResult(Error.USERNAME_TOO_LONG)
            return
        }
        if (username.contains(" ")) {
            onRegisterResult(Error.USERNAME_INVALID)
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onRegisterResult(Error.EMAIL_INVALID)
            return
        }
        if (password.length < 6) {
            onRegisterResult(Error.PASSWORD_TOO_SHORT)
            return
        }
        if (password == confirmPassword) {
            viewModelScope.launch {
                if (userService.isUsernameSet(username)) {
                    onRegisterResult(Error.USERNAME_TAKEN)
                    return@launch
                }
                try {
                    val result = auth.createUserWithEmailAndPassword(email, password).await()
                    if (result.user != null) {
                        val newUser = User(
                            username = username,
                        )
                        userService.create(newUser)
                        onRegisterResult(null)
                    }
                } catch (e: Exception) {
                    if (e.message == "The email address is already in use by another account.") {
                        onRegisterResult(Error.EMAIL_TAKEN)
                    } else {
                        onRegisterResult(Error.UNKNOWN)
                    }
                }
            }
        } else {
            onRegisterResult(Error.PASSWORDS_DO_NOT_MATCH)
        }
    }
}