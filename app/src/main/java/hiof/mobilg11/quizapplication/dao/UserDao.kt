package hiof.mobilg11.quizapplication.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import hiof.mobilg11.quizapplication.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDao @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val gameDao: GameDao
) {
    private val COLLECTION: String = "users"

    //    val ISO_8601_FORMATTER = DateTimeFormatter.ISO_DATE_TIME
    suspend fun createUser(user: User) {
        Log.d("UserDao", "Trying to create user...")
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val user = hashMapOf(
            "username" to user.username,
            "games" to mutableListOf<DocumentReference>(),
            "friends" to mutableListOf<DocumentReference>(),
            "onlineGameSessions" to mutableListOf<DocumentReference>(),
        )
        if (uid != null) {
            firestore.collection(COLLECTION)
                .document(uid)
                .set(user)
                .await()
        }
    }

    suspend fun setUsername(username: String): Boolean {
        val uid = auth.currentUser?.uid
        val isValid = usernameIsValid(username)
        if (uid != null && isValid) {
            try {
                firestore.collection(COLLECTION)
                    .document(uid)
                    .update("username", username)
                    .await()
                println("Username successfully updated")
                return true
            } catch (e: Exception) {
                println("Error updating username: $e")
            }
        }
        return false
    }

    private suspend fun usernameIsValid(username: String): Boolean {
        val querySnapshot = try {
            firestore.collection(COLLECTION)
                .whereEqualTo("username", username)
                .get()
                .await()
        } catch (e: Exception) {
            println("Error getting documents: $e")
            return false
        }
        return querySnapshot.isEmpty
    }

    suspend fun isUsernameSet(username: String): Boolean {
        return firestore.collection(COLLECTION)
            .whereEqualTo("username", username)
            .get()
            .await()
            .isEmpty
            .not()
    }

    fun getUser(uuid: String): Flow<User?> {
        return firestore.collection(COLLECTION)
            .document(uuid)
            .dataObjects()
    }

    suspend fun findUser(username: String): List<User?> {
        return firestore.collection(COLLECTION)
            .get()
            .await()
            .toObjects(User::class.java)
            .filter { user ->
                user.username.contains(username, ignoreCase = true)
            }
    }

}