import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiplayerPage(navController: NavController) {

    var showSearchField by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val searchResults = listOf(
        "Ola",
        "olabil",
    )

    val startedGames = listOf(
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(showSearchField) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        Log.d("MultiplayerPage", "Searching for $searchQuery")
                    }
                )
            )

            LazyColumn(content = {
                items(searchResults.size) { index ->
                    Text(
                        text = searchResults[index],
                    )
                }
            })

        } else {
            Button(
                onClick = { showSearchField = true},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Challenge a Friend",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyColumn(content = {
                items(startedGames.size) { index ->
                    Button(
                        onClick = {
                            Log.d("MultiplayerPage", "Clicked on ${startedGames[index]}")
                            navController.navigate(Screen.MultiplayerLobby.createRoute(index.toString()))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = startedGames[index],
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            })
        }
    }
}