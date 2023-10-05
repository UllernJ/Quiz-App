package hiof.mobilg11.quizapplication.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Seeder(private val db: FirebaseFirestore) {
    @OptIn(DelicateCoroutinesApi::class)
    fun seed() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("Seeder", "Seeding...")
            try {
                val url = URL("https://opentdb.com/api.php?amount=5000")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8"))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    reader.close()

                    val responseData = response.toString()
                    val gson = Gson()
                    val triviaResponse = gson.fromJson(responseData, TriviaResponse::class.java)

                    var categories = db.collection("category").get().await().documents.map { it.getString("name") }
                    Log.d("Seeder", "Categories: $categories")
                    triviaResponse.results.map { triviaQuestion ->
                        val categoryText = removeHTMLTags(triviaQuestion.category)
                        if (!categories.contains(categoryText)) {
                            val sanitizedChoices = triviaQuestion.incorrect_answers.map { removeHTMLTags(it) } +
                                    listOf(removeHTMLTags(triviaQuestion.correct_answer))
                            val newCategoryRef = db.collection("category").document()
                            newCategoryRef.set(mapOf("name" to categoryText))
                            db.collection("question")
                                .add(
                                mapOf(
                                    "question" to removeHTMLTags(triviaQuestion.question),
                                    "choices" to sanitizedChoices.shuffled(),
                                    "correctAnswer" to removeHTMLTags(triviaQuestion.correct_answer),
                                    "category" to newCategoryRef
                                )
                            )
                            categories = categories.plus(categoryText)
                            Log.d("Seeder", "Added new category '$categoryText'")
                        } else {
                            println("Category '$categoryText' already exists, adding question...")
                            db.collection("question").add(
                                mapOf(
                                    "question" to removeHTMLTags(triviaQuestion.question),
                                    "choices" to triviaQuestion.incorrect_answers.map { removeHTMLTags(it) } +
                                            listOf(removeHTMLTags(triviaQuestion.correct_answer)),
                                    "correctAnswer" to removeHTMLTags(triviaQuestion.correct_answer),
                                    "category" to db.collection("category").whereEqualTo("name", categoryText).get().await().documents.first().reference
                                )
                            )
                        }
                    }

                    Log.d("Seeder", "Seeding successful!")

                } else {
                    Log.e("Seeder", "HTTP request failed with response code $responseCode")
                }
            } catch (e: Exception) {
                Log.e("Seeder", "Error: ${e.message}")
            }
        }
    }
    private fun removeHTMLTags(string: String): String {
        return string
            .replace("&quot;", "\"")
            .replace("&#039;", "'")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&rdquo;?", "\"")
            .replace("&ldquo;", "\"")
            .replace("&uuml;", "ü")
            .replace("&eacute;", "é")
            .replace("&aacute;", "á")
    }
}

data class TriviaResponse(
    val response_code: Int,
    val results: List<TriviaQuestion>
)

data class TriviaQuestion(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)