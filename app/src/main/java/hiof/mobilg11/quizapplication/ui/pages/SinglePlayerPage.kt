package hiof.mobilg11.quizapplication.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.model.Category

var categories: List<Category> = listOf(
    Category("General Knowledge", "General Knowledge"),
    Category("Books", "Entertainment: Books"),
    Category("Film", "Entertainment: Film"),
    Category("Music", "Entertainment: Music"),
    Category("Musicals & Theatres", "Entertainment: Musicals & Theatres"),
    Category("Television", "Entertainment: Television"),
    Category("Video Games", "Entertainment: Video Games"),
    Category("Board Games", "Entertainment: Board Games"),
    Category("Science & Nature", "Science & Nature"),
    Category("Computers", "Science: Computers"),
    Category("Mathematics", "Science: Mathematics"),
) //todo fetch from api/db

var selectedCategory: List<Category> = listOf()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePlayerPage(navController: NavController) {

    val searchQuery = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = searchQuery.value,
            onValueChange = { newValue ->
                searchQuery.value = newValue
                selectedCategory = filterCategories(searchQuery.value, categories)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {
                Text("Search Categories")
            }
        )
        if(searchQuery.value.isEmpty()) {
            selectedCategory = categories
        }
        selectedCategory.forEach { label ->
            Button(
                onClick = {
                          Log.i("INFO", "${label.name} clicked") //todo go to quiz page with category parameter
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = label.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun filterCategories(searchQuery: String, categories: List<Category>): List<Category> {
    return categories.filter { category ->
        category.name.contains(searchQuery, ignoreCase = true)
    }
}