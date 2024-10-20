package com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.malakezzat.banquemisr.challenge05.Utils.NetworkUtils
import com.malakezzat.banquemisr.challenge05.data.remote.ApiState
import com.malakezzat.banquemisr.challenge05.model.MovieResponse
import com.malakezzat.banquemisr.challenge05.ui.DetailsScreen
import com.malakezzat.banquemisr.challenge05.ui.NoInternetScreen
import com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.viewmodel.UpcomingScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.theme.AppColors
import kotlinx.coroutines.delay

private const val TAG = "UpcomingScreen"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpcomingScreen(viewModel: UpcomingScreenViewModel,
                   navController: NavController
){
    val context = LocalContext.current
    val upcomingState by viewModel.upcoming.collectAsState()
    var upcomingResponse by remember { mutableStateOf(MovieResponse()) }
    var isLoading by remember { mutableStateOf(false) }
    val isNetworkAvailable = NetworkUtils.isNetworkAvailable(context)

    val isRefreshing by viewModel.isRefreshing.collectAsState(initial = false)
    val pullRefreshState = rememberPullRefreshState( refreshing = isRefreshing , onRefresh =  {
        if(isNetworkAvailable) {
            viewModel.refreshUpcoming()
        } else {
            viewModel.getUpcomingLocal()
        }
    })

    LaunchedEffect(Unit) {
        if(isNetworkAvailable) {
            viewModel.getUpcoming()
        } else {
            viewModel.getUpcomingLocal()
        }
    }

    when(upcomingState){
        is ApiState.Error -> {
            isLoading = false
            Log.i(TAG, "ListScreen: ${(upcomingState as ApiState.Error).message}")
            Toast.makeText(context, "Error Fetching Movies", Toast.LENGTH_SHORT).show()
        }
        ApiState.Loading -> {
            isLoading = true
        }
        is ApiState.Success -> {
            isLoading = false
            upcomingResponse = (upcomingState as ApiState.Success).data
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState()),
    ) {
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = AppColors.Rose
                )
            }
        } else if (upcomingResponse.results.isNotEmpty()) {
            Column {
                Text(
                    "Upcoming ⏭\uFE0F",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        textMotion = TextMotion.Animated,

                        ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow {

                    items(upcomingResponse.results.size) { movie ->
                        MovieItem(upcomingResponse.results[movie]) { movieId ->
                            navController.navigate(DetailsScreen(movieId))
                        }
                    }
                }
            }
        } else if (!isNetworkAvailable && !isLoading) {
            NoInternetScreen(Modifier.align(Alignment.Center))
        }
        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))

    }
}
