package hiof.mobilg11.quizapplication.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
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
    suspend fun createUser() {
        Log.d("UserDao", "Trying to create user...")
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val user = hashMapOf(
            "username" to "",
            "games" to mutableListOf<DocumentReference>(),
//            "created" to ISO_8601_FORMATTER.format(LocalDateTime.now()),
//            "lastLogin" to ISO_8601_FORMATTER.format(LocalDateTime.now()),
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

    suspend fun isUsernameSet(): Boolean {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            try {
                val document = firestore.collection(COLLECTION)
                    .document(uid)
                    .get()
                    .await()
                val username = document.data?.get("username") as String?
                return !username.isNullOrEmpty()
            } catch (e: Exception) {
                println("Error checking if username is set: $e")
            }
        }
        return false
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

//    suspend fun getLastChallengedUsers(username: String, amount: Int): List<String> {
//        val games = gameDao.getAllGamesByUsername(username)
//
//        val sortedGames = games.sortedByDescending { game -> game.lastUpdated }
//        val filteredGames = sortedGames.filter { game ->
//            game.gameState == GameState.FINISHED
//        }
//
//        val lastChallengedUsers = mutableListOf<String>()
//
//        for (game in filteredGames) {
//            if (lastChallengedUsers.size >= amount) {
//                break
//            }
//
//            var foundUser = ""
//
//            if (game.host == username) foundUser = game.opponent
//            else foundUser = game.host
//
//            if (!lastChallengedUsers.contains(foundUser)) {
//                lastChallengedUsers.add(foundUser)
//            }
//        }
//
//
//        return lastChallengedUsers
//    }

}