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
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
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
    val currentQuestionIndex = viewModel.currentQuestionIndex.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        renderGameInfo(game.value, viewModel, categories.value)
        Spacer(modifier = Modifier.height(16.dp))
        RenderQuestionsAndAnswers(
            game.value,
            questions.value,
            currentQuestionIndex.value,
            viewModel,
            navController
        )
    }
}

@Composable
private fun renderGameInfo(
    game: MultiplayerGame,
    viewModel: MultiplayerPlayViewModel,
    categories: List<Category>
) {
    val isOurTurnToPick = viewModel.isOurTurnToPick()
    val amIOpponent = viewModel.amIOpponent()
    val showCategory = remember { mutableStateOf(true) }

    Text(text = if (!amIOpponent) "${game.host} vs ${game.opponent}" else "${game.opponent} vs ${game.host}")
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = if (!amIOpponent) "${game.hostScore} - ${game.opponentScore}" else "${game.opponentScore} - ${game.hostScore}")
    Spacer(modifier = Modifier.height(16.dp))

    if (isOurTurnToPick && categories.isNotEmpty() && showCategory.value) {
        RenderCategoryButtons(categories, viewModel) {
            showCategory.value = false
        }
    }
}

@Composable
private fun RenderCategoryButtons(
    categories: List<Category>,
    viewModel: MultiplayerPlayViewModel,
    callback: () -> Unit
) {
    Text(text = "It's your turn to pick a category")
    Spacer(modifier = Modifier.height(16.dp))
    categories.forEach { category ->
        Button(
            onClick = {
                viewModel.fetchQuestions(category)
                callback()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = category.name)
        }
    }
}

@Composable
private fun RenderQuestionsAndAnswers(
    game: MultiplayerGame,
    questions: List<Question>,
    currentQuestionIndex: Int,
    viewModel: MultiplayerPlayViewModel,
    navController: NavController
) {
    var hasFinishedRound by remember { mutableStateOf(false) }

    if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
        val question = questions[currentQuestionIndex]
        QuestionDisplay(
            question = question,
            onAnswerSelected = { isCorrect ->
                viewModel.answerQuestion(isCorrect)
            }
        )
    }

    if (currentQuestionIndex == 3 && !hasFinishedRound) {
        navController.navigate(Screen.Home.route)
        viewModel.finishRound()
        hasFinishedRound = true
    }
}
