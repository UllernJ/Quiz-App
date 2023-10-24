package hiof.mobilg11.quizapplication.ui.screen.multiplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.shared.QuestionDisplay
import hiof.mobilg11.quizapplication.viewmodels.MultiplayerPlayViewModel

@Composable
fun MultiplayerPlayScreen(
    navController: NavController,
    viewModel: MultiplayerPlayViewModel = hiltViewModel()
) {

    val game = viewModel.game.collectAsState()
    val categories = viewModel.categories.collectAsState()
    val questions = viewModel.questions.collectAsState()
    var selectedCategory by remember { mutableStateOf("") }
    val currentQuestionIndex = viewModel.currentQuestionIndex.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (game.value.roundQuestionsReferences.size != 3) {
            Text(text = if (!viewModel.amIOpponent()) "${game.value.host} vs ${game.value.opponent}" else "${game.value.opponent} vs ${game.value.host}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = if (!viewModel.amIOpponent()) "${game.value.hostScore} - ${game.value.opponentScore}" else "${game.value.opponentScore} - ${game.value.hostScore}")
            Spacer(modifier = Modifier.height(16.dp))
            if (viewModel.isOurTurnToPick() && categories.value.isNotEmpty() && selectedCategory.isBlank()) {
                Text(text = "It's your turn to pick a category")
                Spacer(modifier = Modifier.height(16.dp))
                categories.value.subList(0, 3).forEach { category ->
                    Button(
                        onClick = {
                            selectedCategory = category.name
                            viewModel.fetchQuestions(category)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = category.name)
                    }
                }
            }

            if (questions.value.isNotEmpty() && currentQuestionIndex.value < questions.value.size) {
                QuestionDisplay(
                    question = questions.value[currentQuestionIndex.value],
                    onAnswerSelected = { isCorrect ->
                        viewModel.answerQuestion(isCorrect)
                    }
                )
            }

            if (currentQuestionIndex.value == 3) {
                navController.navigate(Screen.MultiplayerLobby.createRoute(game.value.uuid))
            }
        } else if(game.value.roundQuestionsReferences.isNotEmpty()) {
            Text(text = "Category: ${game.value.categoriesPlayedReferences.last()}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = if (!viewModel.amIOpponent()) "${game.value.host} vs ${game.value.opponent}" else "${game.value.opponent} vs ${game.value.host}")
            Spacer(modifier = Modifier.height(16.dp))

            if (game.value.categoriesPlayedReferences.isNotEmpty() && currentQuestionIndex.value < game.value.roundQuestionsReferences.size) {
                QuestionDisplay(
                    question = game.value.roundQuestionsReferences[currentQuestionIndex.value],
                    onAnswerSelected = { isCorrect ->
                        viewModel.answerQuestion(isCorrect)
                    }
                )
            }

            if(currentQuestionIndex.value == 3) {
                navController.navigate(Screen.MultiplayerLobby.createRoute(game.value.uuid))
            }

        }
    }
}

@Composable
fun PickingState() {

}

@Composable
fun AnswerState() {

}