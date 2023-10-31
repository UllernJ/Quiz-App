package hiof.mobilg11.quizapplication.utils

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun PlaySoundEffect(effect: Int) {
    val context = LocalContext.current
    val sound = MediaPlayer.create(context, effect)

    sound.start()

    sound.setOnCompletionListener {
        sound.release()
    }
}