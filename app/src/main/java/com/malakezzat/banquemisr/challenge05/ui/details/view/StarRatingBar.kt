package com.malakezzat.banquemisr.challenge05.ui.details.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kotlin.math.floor
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun StarRatingBar(rating: Double, modifier: Modifier = Modifier) {
    val maxRating = 10.0
    val stars = 5
    val ratingPerStar = maxRating / stars


    val fullStars = floor(rating / ratingPerStar).toInt()
    val partialStar = (rating / ratingPerStar) - fullStars

    Row(modifier = modifier) {
        for (i in 1..stars) {
            when {
                i <= fullStars -> {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Full star",
                        tint = Color.Yellow,
                        modifier = Modifier.size(24.dp)
                    )
                }
                i == fullStars + 1 && partialStar > 0 -> {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Partial star background",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Icon(
                            imageVector = HalfStar,
                            contentDescription = "Partial star fill",
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(24.dp)
                                .offset(x = (-3).dp)
                                .clip(RectangleShape)
                                .fillMaxWidth(fraction = partialStar.toFloat())
                        )
                    }
                }
                else -> {
                    // Draw an empty star
                    Icon(
                        imageVector = Icons.Default.StarOutline,
                        contentDescription = "Empty star",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

val HalfStar: ImageVector
    get() {
        if (_halfStar != null) {
            return _halfStar!!
        }
        _halfStar = materialIcon(name = "Filled.HalfStar") {
            materialPath {
                moveTo(12.0f, 17.27f)
                lineTo(9.19f, 21.0f)
                lineToRelative(1.64f, -7.03f)
                lineToRelative(-5.46f, -4.73f)
                lineToRelative(7.19f, -0.61f)
                close()
            }
        }
        return _halfStar!!
    }

private var _halfStar: ImageVector? = null




