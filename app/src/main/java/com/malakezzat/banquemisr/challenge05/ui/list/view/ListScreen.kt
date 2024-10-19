package com.malakezzat.banquemisr.challenge05.ui.list.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.malakezzat.banquemisr.challenge05.R
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.data.remote.RetrofitHelper.BASE_URL
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import com.malakezzat.banquemisr.challenge05.model.Result
import com.malakezzat.banquemisr.challenge05.ui.list.viewmodel.ListScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.theme.AppColors

const val TAG = "ListScreen"

@Composable
fun ListScreen(viewModel: ListScreenViewModel,
               navController: NavController){

    val nowPlayingState by viewModel.nowPlaying.collectAsState()
    val popularState by viewModel.popular.collectAsState()
    val upcomingState by viewModel.upcoming.collectAsState()
    var nowPlayingResponse by remember { mutableStateOf(MovieResponse()) }

    LaunchedEffect(Unit) {
        viewModel.getNowPlaying()
    }

    when(nowPlayingState){
        is ApiState.Error -> {
            Log.i(TAG, "ListScreen: ${(nowPlayingState as ApiState.Error).message}")
        }
        ApiState.Loading -> {
            //TODO Make Loading
        }
        is ApiState.Success -> {
            nowPlayingResponse = (nowPlayingState as ApiState.Success).data
        }
    }
    if(nowPlayingResponse.results.isNotEmpty()) {
    LazyRow {

            items(nowPlayingResponse.results.size) { movie ->
                MovieItem(nowPlayingResponse.results[movie]){
                    //TODO click
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = AppColors.Lavender
            )
        }
    }


}

@Composable
fun MovieItem(movie: Result, onClick: () -> Unit) {
    val baseUrlImages = "https://image.tmdb.org/t/p/w500/"

    val visible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible.value = true
    }

    // Make the Card width fixed and clickable
    AnimatedVisibility(
        visible = visible.value,
        enter = fadeIn(animationSpec = tween(700)) + expandVertically(animationSpec = tween(700)) + scaleIn(animationSpec = tween(700)),
        exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300)) + scaleOut(animationSpec = tween(300))
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .width(250.dp)  // Set card width to accommodate image and text
                .clickable { onClick() }  // Make the card clickable
                .shadow(6.dp),  // Shadow to give depth
            elevation = CardDefaults.cardElevation(12.dp),  // Elevation for depth
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                // Movie image (500x750)
                ImageWithShimmer(
                    imageUrl = "$baseUrlImages${movie.poster_path.substring(1)}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(375.dp)  // Maintain 2:3 aspect ratio (500x750 scaled to dp)
                )

                // Text section (title and release date) with space below the image
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = 100f
                            )
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White, // White text for contrast
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 2,  // Restrict title to two lines
                        overflow = TextOverflow.Ellipsis // Ellipsis for overflow
                    )
                    Spacer(modifier = Modifier.height(4.dp)) // Add a small space between title and date
                    Text(
                        text = "Release Date: ${movie.release_date}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.LightGray
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ImageWithShimmer(imageUrl: String, contentDescription: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .build()
    )

    Box(modifier = modifier) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize() // Ensure the image fills the entire box
                .clip(RoundedCornerShape(16.dp))  // Rounded corners
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            ShimmerEffect(modifier = Modifier.matchParentSize())
        }
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val xShimmer by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(xShimmer, 0f),
        end = Offset(xShimmer + 200f, 200f)
    )

    Spacer(
        modifier = modifier
            .background(brush)
    )
}
