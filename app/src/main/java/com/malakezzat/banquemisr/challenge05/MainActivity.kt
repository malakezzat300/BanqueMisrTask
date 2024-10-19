package com.malakezzat.banquemisr.challenge05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.malakezzat.banquemisr.challenge05.ui.theme.MalakEzzatTaskTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.malakezzat.banquemisr.challenge05.data.remote.MovieApiService
import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSourceImpl
import com.malakezzat.banquemisr.challenge05.data.remote.RetrofitHelper
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepositoryImpl
import com.malakezzat.banquemisr.challenge05.ui.DetailsScreen
import com.malakezzat.banquemisr.challenge05.ui.NowPlayingScreen
import com.malakezzat.banquemisr.challenge05.ui.Main
import com.malakezzat.banquemisr.challenge05.ui.PopularScreen
import com.malakezzat.banquemisr.challenge05.ui.UpcomingScreen
import com.malakezzat.banquemisr.challenge05.ui.details.view.DetailsScreen
import com.malakezzat.banquemisr.challenge05.ui.details.viewmodel.DetailsScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.details.viewmodel.DetailsScreenViewModelFactory
import com.malakezzat.banquemisr.challenge05.ui.list.view.NowPlayingScreen
import com.malakezzat.banquemisr.challenge05.ui.list.view.PopularScreen
import com.malakezzat.banquemisr.challenge05.ui.list.view.UpcomingScreen
import com.malakezzat.banquemisr.challenge05.ui.list.viewmodel.ListScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.list.viewmodel.ListScreenViewModelFactory
import com.malakezzat.banquemisr.challenge05.ui.main.view.MovieScreen

class MainActivity : ComponentActivity() {
    private val repo by lazy {
        MoviesRepositoryImpl.getInstance(
            MoviesRemoteDataSourceImpl.
            getInstance( RetrofitHelper.getInstance().create(MovieApiService::class.java)))
    }
    private val listScreenViewModelFactory by lazy {
        ListScreenViewModelFactory(repo)
    }
    private val detailsScreenViewModelFactory by lazy {
        DetailsScreenViewModelFactory(repo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MalakEzzatTaskTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Main
                ) {
                    composable<Main> {
                        val viewModel : ListScreenViewModel = viewModel(factory = listScreenViewModelFactory)
                        MovieScreen(viewModel,navController)
                    }
                    composable<NowPlayingScreen> {
                        val viewModel : ListScreenViewModel = viewModel(factory = listScreenViewModelFactory)
                        NowPlayingScreen(viewModel,navController)
                    }
                    composable<PopularScreen> {
                        val viewModel : ListScreenViewModel = viewModel(factory = listScreenViewModelFactory)
                        PopularScreen(viewModel,navController)
                    }
                    composable<UpcomingScreen> {
                        val viewModel : ListScreenViewModel = viewModel(factory = listScreenViewModelFactory)
                        UpcomingScreen(viewModel,navController)
                    }
                    composable<DetailsScreen> {
                        val args = it.toRoute<DetailsScreen>()
                        val viewModel : DetailsScreenViewModel = viewModel(factory = detailsScreenViewModelFactory)
                        DetailsScreen(viewModel,navController,args.movieId)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MalakEzzatTaskTheme {

    }
}

