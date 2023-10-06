package hiof.mobilg11.quizapplication.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hiof.mobilg11.quizapplication.dao.CategoryDao
import hiof.mobilg11.quizapplication.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePlayerPage(callback: (DocumentReference) -> Unit) {
    val categoryDao = CategoryDao(Firebase.firestore)
    val categories = remember { mutableStateOf(listOf<Category>()) }
    val searchQuery = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf<List<Category>>(emptyList()) }
    val scrollState = rememberScrollState()

    categoryDao.getAll {
        categories.value = it
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = searchQuery.value,
            onValueChange = { newValue ->
                searchQuery.value = newValue
                selectedCategory.value = filterCategories(searchQuery.value, categories.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {
                Text("Search Categories")
            }
        )

        if (searchQuery.value.isEmpty()) {
            selectedCategory.value = categories.value
        }

        if (selectedCategory.value.isEmpty()) {
            Text(
                text = "No categories found",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            selectedCategory.value.forEach { label ->
                Button(
                    onClick = {
                        label.documentReference?.let { callback(it) }
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
}

fun filterCategories(searchQuery: String, categories: List<Category>): List<Category> {
    return categories.filter { category ->
        category.name.contains(searchQuery, ignoreCase = true)
    }
}