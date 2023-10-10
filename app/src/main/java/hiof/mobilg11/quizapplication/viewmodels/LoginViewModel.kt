package hiof.mobilg11.quizapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(email: String, password: String, onLoginResult: (Boolean) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            onLoginResult(false)
        } else {
            viewModelScope.launch {
                try {
                    val result = auth.signInWithEmailAndPassword(email, password).await()
                    onLoginResult(result.user != null)
                } catch (e: Exception) {
                    onLoginResult(false)
                }
            }
        }
    }
}