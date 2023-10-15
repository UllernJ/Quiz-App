package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userService: UserService,
    private val auth: FirebaseAuth
) : ViewModel() {

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        confirmPassword: String,
        onRegisterResult: (Boolean) -> Unit
    ) {
        if (password == confirmPassword) {
            viewModelScope.launch {
                try {
                    val result = auth.createUserWithEmailAndPassword(email, password).await()
                    if (result.user != null) {
                        onRegisterResult(true)
                        userService.createUser()
                    }
                } catch (e: Exception) {
                    onRegisterResult(false)
                }
            }
        } else {
            onRegisterResult(false)
        }
    }
}