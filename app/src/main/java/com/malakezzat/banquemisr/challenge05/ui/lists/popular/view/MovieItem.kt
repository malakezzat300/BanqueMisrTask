package com.malakezzat.banquemisr.challenge05.ui.lists.popular.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.malakezzat.banquemisr.challenge05.model.Result
import com.malakezzat.banquemisr.challenge05.ui.lists.view.ImageWithShimmer
import com.malakezzat.banquemisr.challenge05.ui.theme.AppColors

@Composable
fun MovieItem(movie: Result, onClick: (Long) -> Unit) {
    val baseUrlImages = "https://image.tmdb.org/t/p/w500/"

    val animatedScale = remember { Animatable(0.8f) }
    val animatedAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animatedScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, easing = LinearEasing)
        )
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, easing = LinearEasing)
        )
    }

    Card(
        modifier = Modifier
            .clickable { onClick(movie.id) }
            .shadow(6.dp)
            .graphicsLayer(
                scaleX = animatedScale.value,
                scaleY = animatedScale.value,
                alpha = animatedAlpha.value
            ),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            ImageWithShimmer(
                imageUrl = "$baseUrlImages${movie.poster_path.substring(1)}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            ){ modifier ->
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    AppColors.RoseDark.copy(alpha = 0.6f),
                                    AppColors.RoseDark
                                ),
                                startY = 0f,
                            )
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.dp),
                        color = AppColors.WhiteLight
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "\uD83D\uDCC5 ${movie.release_date}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.WhiteLight
                    )
                }

            }
        }
    }
}