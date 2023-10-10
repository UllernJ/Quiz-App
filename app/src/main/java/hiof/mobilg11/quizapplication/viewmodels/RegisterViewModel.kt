package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
                    onRegisterResult(result.user != null)
                } catch (e: Exception) {
                    onRegisterResult(false)
                }
            }
        } else {
            onRegisterResult(false)
        }
    }
}