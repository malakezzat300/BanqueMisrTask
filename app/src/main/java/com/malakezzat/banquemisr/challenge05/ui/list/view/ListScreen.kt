package com.malakezzat.banquemisr.challenge05.ui.list.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.malakezzat.banquemisr.challenge05.R
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.data.remote.RetrofitHelper.BASE_URL
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import com.malakezzat.banquemisr.challenge05.model.Result
import com.malakezzat.banquemisr.challenge05.ui.list.viewmodel.ListScreenViewModel

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

    LazyRow {
        if(nowPlayingResponse.results.isNotEmpty()) {
            items(nowPlayingResponse.results.size) { movie ->
                MovieItem(nowPlayingResponse.results[movie])
            }
        } else {
            item {
                CircularProgressIndicator()
            }
        }
    }

}

@Composable
fun MovieItem(movie: Result) {
    val baseUrlImages = "https://image.tmdb.org/t/p/w500/"
    Column {
        Image(painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$baseUrlImages${movie.poster_path.substring(1)}")
                .crossfade(true)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .build()
        ), contentDescription = movie.title)
        Text(text = movie.title)
        Text(text = movie.release_date)
    }
}
