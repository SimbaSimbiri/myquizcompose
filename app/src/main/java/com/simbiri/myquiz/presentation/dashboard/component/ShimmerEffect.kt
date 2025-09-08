package com.simbiri.myquiz.presentation.dashboard.component

import android.R.attr.repeatMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    shimmerColor: Color = MaterialTheme.colorScheme.surface
) {

    val shimmerColors = listOf(
        shimmerColor.copy(.6f),
        shimmerColor.copy(.2f),
        shimmerColor.copy(.6f)
    )

    val transition = rememberInfiniteTransition(label= "")
    val translateAnimation = transition.animateFloat(
        initialValue = 0F ,
        targetValue = 1000F,
        label = "Shimmer Loading Anim",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1300),
            repeatMode = RepeatMode.Restart
        )
    )
    val brush =  Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value
        )

    )
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush)
        )

    }
}

@Preview
@Composable
fun ShimmerEffectPreview() {
    ShimmerEffect(modifier =
        Modifier.fillMaxWidth()
        .height(250.dp)
        .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}