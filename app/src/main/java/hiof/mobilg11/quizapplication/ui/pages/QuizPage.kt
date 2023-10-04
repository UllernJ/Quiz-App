import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hiof.mobilg11.quizapplication.dao.QuizDao
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.model.Quiz

@Composable
fun QuizPage() {

    val quizDao = QuizDao(Firebase.firestore)
    var quizList by remember { mutableStateOf(listOf<Quiz>()) }

    LaunchedEffect(Unit) {
        quizDao.getAllQuiz { fetchedQuizList ->
            quizList = fetchedQuizList
        }
    }

    if (quizList.isNotEmpty()) {
        val quiz: Quiz = quizList[0]


// todo cleanup
//    val questionOne = Question(
//        question = "What is the capital of Norway?",
//        choices = listOf("Oslo", "Bergen", "Trondheim", "Stavanger"),
//        correctAnswer = "Oslo"
//    )
//    val questionTwo = Question(
//        question = "What is the capital of Sweden?",
//        choices = listOf("Oslo", "Bergen", "Stockholm", "Stavanger"),
//        correctAnswer = "Stockholm"
//    )
//    val questionThree = Question(
//        question = "What is the capital of Denmark?",
//        choices = listOf("Oslo", "Copenhagen", "Trondheim", "Stavanger"),
//        correctAnswer = "Copenhagen"
//    )
//    val quiz = Quiz(
//        questions = listOf(questionOne, questionTwo, questionThree),
//        title = "Capitals of Scandinavia",
//        description = "A quiz about the capitals of Scandinavia",
//        category = Category("Geography", "A quiz about geography")
//    )

        var currentQuestionIndex by remember { mutableIntStateOf(0) }
        var score by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentQuestionIndex >= quiz.questions.size) {
                Text(
                    text = "You got $score out of ${quiz.questions.size} correct",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        currentQuestionIndex = 0
                        score = 0
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Restart",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                val currentQuestion = quiz.questions[currentQuestionIndex]

                Text(text = currentQuestion.question, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))

                val choices = currentQuestion.choices
                choices.forEach { choice ->
                    Button(
                        onClick = {
                            if (choice == currentQuestion.correctAnswer) {
                                score++
                            }
                            currentQuestionIndex++
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = choice.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    } else {
        Text(
            text = "Loading...",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            textAlign = TextAlign.Center
            )
    }
}

