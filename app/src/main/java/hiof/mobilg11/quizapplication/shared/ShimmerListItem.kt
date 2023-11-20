package hiof.mobilg11.quizapplication.shared

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


/*
    Inspired by https://www.youtube.com/watch?v=NyO99OJPPec
    Have made some changes to the code to make it more reusable & the code more my own.
 */

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    numberOfItems: Int = 1
) {
    if (isLoading) {
        Spacer(modifier = Modifier.height(12.dp))
        for (index in 0 until numberOfItems) {
            Row() {
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .shimmerEffect(),
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    } else {
        contentAfterLoading()
    }

}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(Size.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ),
        label = ""
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.5f),
                Color.LightGray.copy(alpha = 0.2f)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width, size.height)
        ),
    )

        .onGloballyPositioned {
            size = it.size.toSize()
        }

}
