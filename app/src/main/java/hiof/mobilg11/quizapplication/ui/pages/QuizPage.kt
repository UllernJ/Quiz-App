package hiof.mobilg11.quizapplication.ui

import android.util.Log
import hiof.mobilg11.quizapplication.viewmodels.QuizViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizPage(categoryReference: DocumentReference?) {
    val quizViewModel: QuizViewModel = hiltViewModel()
    val questions by quizViewModel.questions.collectAsState()
    val currentQuestionIndex by quizViewModel.currentQuestionIndex.collectAsState()
    val score by quizViewModel.score.collectAsState()

    if (questions.isEmpty()) {
        quizViewModel.loadQuestions(categoryReference!!)
    }


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
                    quizViewModel.restartQuiz()
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
            var alreadyGuessed by remember { mutableStateOf(false) }
            choices.forEach { choice ->
                val isCorrectAnswer = choice == currentQuestion.correctAnswer
                val defaultColor = MaterialTheme.colorScheme.onPrimaryContainer
                var backgroundColor by remember { mutableStateOf(defaultColor) }

                Button(
                    onClick = {
                        if(alreadyGuessed) return@Button
                        alreadyGuessed = true

                        clicked = true
                        backgroundColor = if(isCorrectAnswer) Color.Green else Color.Red
                        quizViewModel.answerQuestion(isCorrectAnswer)

                        GlobalScope.launch {
                            delay(1000L)
                            clicked = false
                            alreadyGuessed = false
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
                        text = choice,
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

