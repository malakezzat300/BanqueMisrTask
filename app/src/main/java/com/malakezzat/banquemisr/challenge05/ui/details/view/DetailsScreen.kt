package com.malakezzat.banquemisr.challenge05.ui.details.view

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.malakezzat.banquemisr.challenge05.Utils.Details
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.model.MovieDetails
import com.malakezzat.banquemisr.challenge05.ui.details.viewmodel.DetailsScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.view.ImageWithShimmer
import com.malakezzat.banquemisr.challenge05.ui.theme.AppColors
import kotlinx.coroutines.delay

private const val TAG = "DetailsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel,
    navController: NavController,
    movieId : Long){
    val context = LocalContext.current
    val baseUrlImages = "https://image.tmdb.org/t/p/w500/"
    val movieDetailsState by viewModel.movieDetails.collectAsState()
    var movieDetailsResponse by remember { mutableStateOf(MovieDetails()) }
    var isLoading by remember { mutableStateOf(true) }
    var showContent by remember { mutableStateOf(false) }
    var Animate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(movieId)
    }

    when(movieDetailsState){
        is ApiState.Error -> {
            isLoading = false
            Log.i(TAG, "ListScreen: ${(movieDetailsState as ApiState.Error).message}")
            Toast.makeText(context, "Error Fetching Movie Details", Toast.LENGTH_SHORT).show()
        }
        ApiState.Loading -> {
            isLoading = true
        }
        is ApiState.Success -> {
            isLoading = false
            movieDetailsResponse = (movieDetailsState as ApiState.Success).data
            showContent = true
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movie Details")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = AppColors.RoseDark
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = AppColors.Rose)
            }
        } else if (showContent) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .padding(paddingValues),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = Animate,
                        enter = slideInHorizontally(initialOffsetX = { -it }),
                        exit = slideOutHorizontally(targetOffsetX = { -it })
                    ) {
                        Text(
                            text = movieDetailsResponse.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = AppColors.RoseDark,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    AnimatedVisibility(
                        visible = Animate,
                        enter = slideInHorizontally(initialOffsetX = { it }),
                        exit = slideOutHorizontally(targetOffsetX = { it })
                    ) {
                        ImageWithShimmer(
                            imageUrl = "$baseUrlImages${movieDetailsResponse.poster_path.substring(1)}" ?: " ",
                            contentDescription = "movie.title",
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .width(300.dp)
                                .height(450.dp)
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    AnimatedVisibility(
                        visible = Animate,
                        enter = slideInHorizontally(initialOffsetX = { -it }),
                        exit = slideOutHorizontally(targetOffsetX = { -it })
                    ) {
                        Text(
                            text = movieDetailsResponse.overview ?: "No overview available",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            textAlign = TextAlign.Justify
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    AnimatedVisibility(
                        visible = Animate,
                        enter = slideInHorizontally(initialOffsetX = { it }),
                        exit = slideOutHorizontally(targetOffsetX = { it })
                    ) {
                        Text(
                            text = "\uD83C\uDFAD ${Details.formatGenres(movieDetailsResponse.genres)}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    AnimatedVisibility(
                        visible = Animate,
                        enter = slideInHorizontally(initialOffsetX = { -it }),
                        exit = slideOutHorizontally(targetOffsetX = { -it })
                    ) {
                        Text(
                            text = "⏳ ${Details.formatRuntime(movieDetailsResponse.runtime)}  -  \uD83D\uDCC5 ${movieDetailsResponse.release_date}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    AnimatedVisibility(
                        visible = Animate,
                        enter = slideInHorizontally(initialOffsetX = { it }),
                        exit = slideOutHorizontally(targetOffsetX = { it })
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            StarRatingBar(rating = movieDetailsResponse.vote_average)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${movieDetailsResponse.vote_average / 2} (${movieDetailsResponse.vote_count} votes)",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Light
                                )
                            )
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    delay(200)
                    Animate = true
                }

            }
        } else {
            //make a empty screen
        }
    }
}
