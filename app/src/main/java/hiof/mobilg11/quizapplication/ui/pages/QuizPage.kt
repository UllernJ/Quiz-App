package hiof.mobilg11.quizapplication.ui.pages

import hiof.mobilg11.quizapplication.viewmodels.QuizViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.shared.QuestionDisplay
import hiof.mobilg11.quizapplication.shared.ShimmerListItem

@Composable
fun QuizPage(quizViewModel: QuizViewModel = hiltViewModel()) {
    val questions by quizViewModel.questions.collectAsState()
    val currentQuestionIndex by quizViewModel.currentQuestionIndex.collectAsState()
    val score by quizViewModel.score.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentQuestionIndex >= questions.size && questions.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You got $score out of ${questions.size} correct",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        quizViewModel.restartQuiz()
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
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val currentQuestion = questions.getOrNull(currentQuestionIndex)
                ShimmerListItem(
                    isLoading = questions.isEmpty(), contentAfterLoading = {
                        QuestionDisplay(
                            question = currentQuestion!!,
                            onAnswerSelected = { isCorrectAnswer ->
                                quizViewModel.answerQuestion(isCorrectAnswer)
                            })
                    },
                    numberOfItems = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(40.dp)
                        .clip(MaterialTheme.shapes.large)
                )
//                if (currentQuestion != null) {
//                    QuestionDisplay(currentQuestion) { isCorrectAnswer ->
//                        quizViewModel.answerQuestion(isCorrectAnswer)
//                    }
//                } else {
//                    Text(
//                        text = "No more questions.",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
            }
        }
    }
}

