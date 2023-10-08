package hiof.mobilg11.quizapplication.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hiof.mobilg11.quizapplication.dao.QuestionDao
import hiof.mobilg11.quizapplication.model.Question
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun QuizPage(categoryReference: DocumentReference?) {
    val questionDao = QuestionDao(Firebase.firestore)
    var questions by remember { mutableStateOf(listOf<Question<*>>()) }

    if (categoryReference != null && questions.isEmpty()) {
        questionDao.getQuestionsByCategoryReference(categoryReference) {
            questions = it.shuffled().subList(0, 3)
        }
    }


    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentQuestionIndex >= questions.size && questions.isNotEmpty()) {
            Text(
                text = "You got $score out of ${questions.size} correct",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    currentQuestionIndex = 0
                    score = 0
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Restart", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            }
        } else if (questions.isEmpty()) {
            Text(
                text = "Loading...", fontSize = 24.sp, fontWeight = FontWeight.Bold
            )
        } else {
            val currentQuestion = questions[currentQuestionIndex]

            Text(
                text = currentQuestion.question,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            val choices = currentQuestion.choices
            var clicked by remember { mutableStateOf(false) }
            choices.forEach { choice ->
                val isCorrectAnswer = choice == currentQuestion.correctAnswer
                val defaultColor = MaterialTheme.colorScheme.onPrimaryContainer
                var backgroundColor by remember { mutableStateOf(defaultColor) }

                Button(
                    onClick = {
                        clicked = true
                        if (isCorrectAnswer) {
                            score++
                            backgroundColor = Color.Green
                        } else {
                            backgroundColor = Color.Red
                        }
                        GlobalScope.launch {
                            delay(1000L)
                            currentQuestionIndex++
                            clicked = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundColor, contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = choice.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                LaunchedEffect(backgroundColor, clicked) {
                    if (!clicked) {
                        backgroundColor = defaultColor
                    }
                    if (isCorrectAnswer && clicked) {
                        backgroundColor = Color.Green
                    }
                    if (backgroundColor != defaultColor && clicked) {
                        delay(1000L)
                        backgroundColor = defaultColor
                    }
                }
            }
        }
    }
}

