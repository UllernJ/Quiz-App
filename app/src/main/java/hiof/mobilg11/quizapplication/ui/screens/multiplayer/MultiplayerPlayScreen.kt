package hiof.mobilg11.quizapplication.ui.screens.multiplayer

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.model.game.MultiplayerGame
import hiof.mobilg11.quizapplication.shared.QuestionDisplay
import hiof.mobilg11.quizapplication.viewmodels.MultiplayerPlayViewModel

@Composable
fun MultiplayerPlayScreen(
    navController: NavController,
    username: String,
    viewModel: MultiplayerPlayViewModel = hiltViewModel()
) {
    val game = viewModel.game.collectAsState()
    val categories = viewModel.categories.collectAsState()
    val questions = viewModel.questions.collectAsState()
    val currentQuestionIndex = viewModel.currentQuestionIndex.collectAsState()

    viewModel.questionInit(username)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RenderGameInfo(game.value, viewModel, categories.value, username)
        Spacer(modifier = Modifier.height(16.dp))
        RenderQuestionsAndAnswers(
            questions.value,
            currentQuestionIndex.value,
            viewModel,
            navController,
            username
        )
    }
}

@Composable
private fun RenderGameInfo(
    game: MultiplayerGame,
    viewModel: MultiplayerPlayViewModel,
    categories: List<Category>,
    username: String,
) {
    val isOurTurnToPick = viewModel.isOurTurnToPick(username)
    val amIOpponent = viewModel.amIOpponent(username)
    val showCategory = remember { mutableStateOf(true) }

    Text(text = if (!amIOpponent) stringResource(R.string.user_vs_user, game.host, game.opponent) else stringResource(R.string.user_vs_user, game.opponent, game.host))
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = if (!amIOpponent) stringResource(
        R.string.score_vs_score,
        game.hostScore,
        game.opponentScore
    ) else stringResource(
        R.string.score_vs_score,
        game.opponentScore,
        game.hostScore
    ))
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
    Text(text = stringResource(R.string.game_your_turn_category))
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
    questions: List<Question>,
    currentQuestionIndex: Int,
    viewModel: MultiplayerPlayViewModel,
    navController: NavController,
    username: String
) {
    var hasFinishedRound by remember { mutableStateOf(false) }

    if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
        val question = questions[currentQuestionIndex]
        QuestionDisplay(
            question = question,
            onAnswerSelected = { isCorrect ->
                viewModel.answerQuestion(isCorrect, username)
            }
        )
    }

    if (currentQuestionIndex == 3 && !hasFinishedRound) {
        navController.navigate(Screen.Home.route)
        viewModel.finishRound(username)
        hasFinishedRound = true
    }


}
