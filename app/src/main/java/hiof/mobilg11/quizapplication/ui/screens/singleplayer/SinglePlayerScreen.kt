package hiof.mobilg11.quizapplication.ui.screens.singleplayer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.viewmodels.singleplayer.SinglePlayerViewModel

@Composable
fun SinglePlayerScreen(callback: (String) -> Unit) {
    val singlePlayerViewModel: SinglePlayerViewModel = hiltViewModel()
    val categories by singlePlayerViewModel.categories.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val filteredCategories = filterCategories(searchQuery.text, categories)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search categories") },
            colors = TextFieldDefaults.colors(Color.Transparent)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredCategories) { category ->
                CategoryCard(category = category, onClick = { callback(category.name) })
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(12.dp)
            .height(140.dp)
            .width(120.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = category.emoji,
                fontSize = 48.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp
            )
        }
    }
}

fun filterCategories(searchQuery: String, categories: List<Category>): List<Category> {
    return if (searchQuery.isEmpty()) categories
    else categories.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
}