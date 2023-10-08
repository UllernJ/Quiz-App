package hiof.mobilg11.quizapplication.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.user.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val COLLECTION: String = "users"
    val ISO_8601_FORMATTER = DateTimeFormatter.ISO_DATE_TIME
    fun createUser() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val user = hashMapOf(
            "username" to "",
            "games" to mutableListOf<DocumentReference>(),
            "created" to ISO_8601_FORMATTER.format(LocalDateTime.now()),
            "lastLogin" to ISO_8601_FORMATTER.format(LocalDateTime.now()),
            "friends" to mutableListOf<DocumentReference>(),
            "onlineGameSessions" to mutableListOf<DocumentReference>(),
        )
        if (uid != null) {
            db.collection(COLLECTION)
                .document(uid)
                .set(user)
                .addOnSuccessListener {
                    println("User successfully created")
                }
                .addOnFailureListener { e ->
                    println("Error creating user: $e")
                }
        }
    }

    fun setUsername(username: String, callback: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val isValid = usernameIsValid(username)
        if (uid != null && isValid) {
            db.collection(COLLECTION)
                .document(uid)
                .update("username", username)
                .addOnSuccessListener {
                    println("Username successfully updated")
                    callback(true)
                }
                .addOnFailureListener { e ->
                    println("Error updating username: $e")
                    callback(false)
                }
        }
    }

    private fun usernameIsValid(username: String): Boolean {
        var valid = true
        db.collection(COLLECTION)
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { result ->
                if (result.size() > 0) {
                    valid = false
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
        return valid
    }

    fun isUsernameSet(callback: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            db.collection(COLLECTION)
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val username = document.data?.get("username")
                        if (username == null || username == "") {
                            callback(false)
                        }
                    }
                }
        }
    }

    fun getUser(callback: (User) -> Unit) {
        Log.d("UserDao", "Trying to fetch user...")
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            db.collection(COLLECTION)
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    Log.d("UserDao", "User fetched successfully")
                    if (document != null) {
                        val user = document.toObject(User::class.java)
                        if (user != null) {
                            callback(user)
                        }
                    }
                }
        }
    }

}