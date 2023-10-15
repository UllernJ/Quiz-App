package hiof.mobilg11.quizapplication.service

interface AuthService {
    val currentUserUid: String

    suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun signOut()
}