package hiof.mobilg11.quizapplication.dao

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Category

class CategoryDao(private val db: FirebaseFirestore) {

    fun getAll(callback: (List<Category>) -> Unit) {
        val categoryList = mutableListOf<Category>()
        db.collection("category")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.data["name"] as String
                    val documentReference = document.reference
                    val category = Category(name, documentReference)
                    categoryList.add(category)
                }
                callback(categoryList)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
        Log.d(ContentValues.TAG, "Fetched categories: $categoryList")
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