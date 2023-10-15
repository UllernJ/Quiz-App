package hiof.mobilg11.quizapplication.dao

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hiof.mobilg11.quizapplication.model.Category
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryDao @Inject constructor(private val firestore: FirebaseFirestore) {
    private val COLLECTION: String = "category"
    suspend fun getAll(): MutableList<Category> {
        Log.d(ContentValues.TAG, "getAll fetches all categories from firestore...")
        return firestore.collection(COLLECTION)
            .get()
            .await()
            .toObjects(Category::class.java)
            .toMutableList()
    }
}