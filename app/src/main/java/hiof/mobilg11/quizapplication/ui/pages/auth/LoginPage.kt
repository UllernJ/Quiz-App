package hiof.mobilg11.quizapplication.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hiof.mobilg11.quizapplication.R
import hiof.mobilg11.quizapplication.ui.theme.quizIcon
import hiof.mobilg11.quizapplication.viewmodels.LoginViewModel

@Composable
fun LoginPage(
    navController: NavController,
    viewModel: LoginViewModel = LoginViewModel(),
    onLogin: () -> Unit
) {
    var password by remember {
        mutableStateOf(InputType.Password)
    }
    var email by remember {
        mutableStateOf(InputType.Email)
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(quizIcon),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 24.dp)
        )
        TextInput(email)
        TextInput(password)
        Button(
            onClick = {
                viewModel.signInWithEmailAndPassword(email.value, password.value) { success ->
                    if (success) {
                        onLogin()
                    } else {
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Sign in",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account?")
            TextButton(
                onClick = {
                    navController.navigate(R.string.register_page_path.toString())
                },
            ) {
                Text("SIGN UP")
            }
        }
    }
}



