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

                    triviaResponse.results.map { triviaQuestion ->
                        db.collection("question")
                            .whereEqualTo("question", removeHTMLTags(triviaQuestion.question))
                            .get()
                            .addOnSuccessListener { result ->
                                if (result.isEmpty) {
                                    db.collection("question")
                                        .add(
                                            mapOf(
                                                "question" to removeHTMLTags(triviaQuestion.question),
                                                "choices" to (triviaQuestion.incorrect_answers + triviaQuestion.correct_answer).shuffled(),
                                                "correctAnswer" to removeHTMLTags(triviaQuestion.correct_answer),
                                                "category" to removeHTMLTags(triviaQuestion.category)
                                            )
                                        )
                                    }
                                }
                            }


                    val categories = triviaResponse.results.map { it.category }.distinct()
                    categories.map {
                        db.collection("category")
                            .whereEqualTo("name", removeHTMLTags(it))
                            .get()
                            .addOnSuccessListener { result ->
                                if (result.isEmpty) {
                                    db.collection("category")
                                        .add(
                                            mapOf(
                                                "name" to removeHTMLTags(it)
                                            )
                                        )
                                    }
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