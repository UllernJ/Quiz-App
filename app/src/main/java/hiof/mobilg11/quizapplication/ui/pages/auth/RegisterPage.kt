package hiof.mobilg11.quizapplication.ui.pages.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import hiof.mobilg11.quizapplication.ui.theme.registerIcon
import hiof.mobilg11.quizapplication.viewmodels.RegisterViewModel

@Composable
fun RegisterPage(
    viewModel: RegisterViewModel = RegisterViewModel(),
    onLogin: () -> Unit
) {
    var email by remember {
        mutableStateOf(InputType.Email)
    }
    var password by remember {
        mutableStateOf(InputType.Password)
    }
    var confirmPassword by remember {
        mutableStateOf(InputType.ConfirmPassword)
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
            painter = painterResource(registerIcon),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 24.dp)
        )
        TextInput(email)
        TextInput(password)
        TextInput(confirmPassword)
        Button(
            onClick = {
                if (password.value != confirmPassword.value) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.createUserWithEmailAndPassword(
                        email.value,
                        password.value,
                        confirmPassword.value
                    ) { success ->
                        if (success) {
                            Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT)
                                .show()
                            onLogin()
                        } else {
                            Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Sign up",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
