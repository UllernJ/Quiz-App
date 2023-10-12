package hiof.mobilg11.quizapplication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import hiof.mobilg11.quizapplication.dao.UserDao
import hiof.mobilg11.quizapplication.model.user.User
import hiof.mobilg11.quizapplication.service.UserCache
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userCache: UserCache) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onLoginResult: (Boolean) -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            onLoginResult(false)
        } else {
            viewModelScope.launch {
                try {
                    val result = auth.signInWithEmailAndPassword(email, password).await()
                    if(result.user != null) {
                        saveUser()
                        onLoginResult(true)
                    }

                } catch (e: Exception) {
                    onLoginResult(false)
                }
            }
        }
    }
    private fun saveUser() {
        viewModelScope.launch {
            val user = UserDao().getUser(FirebaseAuth.getInstance().currentUser?.uid!!)
            if (user != null) {
                userCache.saveUser(user)
            }
        }
    }

    fun getUser(): User? {
        val user = userCache.getUser()
        Log.d("LoginViewModel", "getUser: $user")
        return user
    }

    fun signOut() {
        userCache.clearUser()
        auth.signOut()
    }

}