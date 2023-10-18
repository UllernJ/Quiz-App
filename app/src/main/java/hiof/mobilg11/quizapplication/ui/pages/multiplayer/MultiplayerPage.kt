import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.Screen
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.viewmodels.MultiplayerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiplayerPage(
    navController: NavController,
    viewModel: MultiplayerViewModel = hiltViewModel()
) {

    var showSearchField by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val searchUserList = viewModel.users.collectAsState()

    val startedGames = listOf(
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
        "Din tur mot\nOla\nOla 1-0 Deg",
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showSearchField) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        if (it.length > 2) {
                            viewModel.findUser(it)
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            Log.d("MultiplayerPage", "Searching for $searchQuery")
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                )
                IconButton(
                    onClick = { showSearchField = false },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Exit")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (searchUserList.value.isNotEmpty()) {
                DisplayUsers(searchUserList.value)
            }
        } else {
            Button(
                onClick = { showSearchField = true },
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

@Composable
private fun DisplayUsers(users: List<User?>) {
    LazyColumn(
        content = {
            items(users.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp, Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(Color.Gray.copy(alpha = 0.1f)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = users[index]?.username ?: "No user found",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    )

                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Challenge Icon",
                        tint = Color.Blue,
                        modifier = Modifier
                            .clickable { /* TODO create instance */ }
                            .padding(16.dp)
                    )
                }
            }
        }
    )
}