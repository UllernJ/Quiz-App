package hiof.mobilg11.quizapplication.ui.screens.singleplayer

import androidx.compose.foundation.background
import hiof.mobilg11.quizapplication.viewmodels.singleplayer.QuizViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.shared.QuestionDisplay
import hiof.mobilg11.quizapplication.shared.ShimmerListItem
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue

@Composable
fun QuizScreen(navController: NavController, quizViewModel: QuizViewModel = hiltViewModel()) {
    val questions by quizViewModel.questions.collectAsState()
    val currentQuestionIndex by quizViewModel.currentQuestionIndex.collectAsState()
    val score by quizViewModel.score.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(listOf(DeepBlue, Color.Black)))
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
                    text = stringResource(R.string.you_got_out_of_correct, score, questions.size),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.back),
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
                        if (currentQuestion != null) {
                            QuestionDisplay(
                                question = currentQuestion,
                                onAnswerSelected = { isCorrectAnswer ->
                                    quizViewModel.answerQuestion(isCorrectAnswer)
                                })
                        }
                    },
                    numberOfItems = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(40.dp)
                        .clip(MaterialTheme.shapes.large)
                )
            }
        }
    }
}

