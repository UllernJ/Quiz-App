package hiof.mobilg11.quizapplication.dao

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Category
import kotlinx.coroutines.tasks.await

class CategoryDao() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val COLLECTION: String = "category"
    suspend fun getAll(): MutableList<Category> {
        val categories = mutableListOf<Category>()
        val querySnapshot = db.collection(COLLECTION).get().await()
        for (document in querySnapshot.documents) {
            val name = document.data?.get("name") as String
            val category = Category(name, document.reference)
            categories.add(category)
        }
        Log.d(ContentValues.TAG, "getAll: $categories")
        return categories
    }

    fun getCategoryByDocRef(docRef: DocumentReference, callback: (Category?) -> Unit) {
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name = document.data?.get("name") as String
                    val category = Category(name)
                    callback(category)
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                callback(null)
            }
    }

}