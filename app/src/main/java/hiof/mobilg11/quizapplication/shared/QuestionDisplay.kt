package hiof.mobilg11.quizapplication.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hiof.mobilg11.quizapplication.model.Question
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuestionDisplay(question: Question, onAnswerSelected: (Boolean) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question.question,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val choices = question.choices
        var alreadyGuessed by remember { mutableStateOf(false) }

        choices.forEach { choice ->
            val isCorrectAnswer = choice == question.correctAnswer
            val defaultColor = MaterialTheme.colorScheme.onPrimaryContainer
            var backgroundColor by remember { mutableStateOf(defaultColor) }

            Button(
                onClick = {
                    if (alreadyGuessed) return@Button
                    alreadyGuessed = true

                    backgroundColor = if (isCorrectAnswer) Color.Green else Color.Red

                    GlobalScope.launch {
                        onAnswerSelected(isCorrectAnswer)
                        delay(1000L)
                        backgroundColor = defaultColor
                        alreadyGuessed = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text(
                    text = choice,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

            }
            LaunchedEffect(backgroundColor, alreadyGuessed) {
                if (!alreadyGuessed) {
                    backgroundColor = defaultColor
                }
                if (isCorrectAnswer && alreadyGuessed) {
                    backgroundColor = Color.Green
                }
                if (backgroundColor != defaultColor && alreadyGuessed) {
                    delay(1000L)
                    backgroundColor = defaultColor
                }
            }
        }
    }
}