package hiof.mobilg11.quizapplication.service.impl

import com.google.firebase.auth.FirebaseAuth
import hiof.mobilg11.quizapplication.service.AuthService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthService {
    override val currentUserUid: String
        get() = auth.currentUser?.uid.orEmpty()

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}