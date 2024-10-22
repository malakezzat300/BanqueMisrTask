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
import com.malakezzat.banquemisr.challenge05.data.local.AppDatabase
import com.malakezzat.banquemisr.challenge05.data.local.MoviesLocalDataSourceImpl
import com.malakezzat.banquemisr.challenge05.data.remote.MovieApiService
import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSourceImpl
import com.malakezzat.banquemisr.challenge05.data.remote.RetrofitHelper
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepositoryImpl
import com.malakezzat.banquemisr.challenge05.ui.DetailsScreen
import com.malakezzat.banquemisr.challenge05.ui.NowPlayingScreen
import com.malakezzat.banquemisr.challenge05.ui.Main
import com.malakezzat.banquemisr.challenge05.ui.PopularScreen
import com.malakezzat.banquemisr.challenge05.ui.SplashScreen
import com.malakezzat.banquemisr.challenge05.ui.UpcomingScreen
import com.malakezzat.banquemisr.challenge05.ui.details.view.DetailsScreen
import com.malakezzat.banquemisr.challenge05.ui.details.viewmodel.DetailsScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.view.NowPlayingScreen
import com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel.NowPlayingScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.popular.view.PopularScreen
import com.malakezzat.banquemisr.challenge05.ui.lists.popular.viewmodel.PopularScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.view.UpcomingScreen
import com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.viewmodel.UpcomingScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.main.view.MovieScreen
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MalakEzzatTaskTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = SplashScreen
                ) {
                    composable<SplashScreen> {
                        SplashScreen(navController)
                    }
                    composable<Main> {
                        val nowPlayingViewModel: NowPlayingScreenViewModel = getViewModel()
                        val popularViewModel: PopularScreenViewModel = getViewModel()
                        val upcomingViewModel: UpcomingScreenViewModel = getViewModel()

                        MovieScreen(
                            nowPlayingViewModel,
                            popularViewModel,
                            upcomingViewModel,
                            navController
                        )
                    }
                    composable<NowPlayingScreen> {
                        val viewModel: NowPlayingScreenViewModel = getViewModel()
                        NowPlayingScreen(viewModel, navController)
                    }
                    composable<PopularScreen> {
                        val viewModel: PopularScreenViewModel = getViewModel()
                        PopularScreen(viewModel, navController)
                    }
                    composable<UpcomingScreen> {
                        val viewModel: UpcomingScreenViewModel = getViewModel()
                        UpcomingScreen(viewModel, navController)
                    }
                    composable<DetailsScreen> {
                        val args = it.toRoute<DetailsScreen>()
                        val viewModel: DetailsScreenViewModel = getViewModel()
                        DetailsScreen(viewModel, navController, args.movieId)
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

