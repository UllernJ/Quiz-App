import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.mobilg11.quizapplication.ui.theme.DeepBlue

@Composable
fun NavBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        IconButton(
            onClick = { navController.navigateUp() },
            content = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}
