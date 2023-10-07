package hiof.mobilg11.quizapplication.dao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val COLLECTION: String = "users"
    fun createUser() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val user = hashMapOf(
            "username" to "",
            "games" to mutableListOf<DocumentReference>(),
            "created" to LocalDateTime.now()
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
        if(uid != null && isValid) {
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
                if(result.size() > 0) {
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
        if(uid != null) {
            db.collection(COLLECTION)
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if(document != null) {
                        val username = document.data?.get("username")
                        if(username == null || username == "") {
                            callback(false)
                        }
                    }
                }
        }
    }

}