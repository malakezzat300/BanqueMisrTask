package com.malakezzat.banquemisr.challenge05

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.malakezzat.banquemisr.challenge05.ui.theme.MalakEzzatTaskTheme
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.malakezzat.banquemisr.challenge05.data.remote.MovieApiService
import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSourceImpl
import com.malakezzat.banquemisr.challenge05.data.remote.RetrofitHelper
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepositoryImpl
import com.malakezzat.banquemisr.challenge05.ui.DetailsScreen
import com.malakezzat.banquemisr.challenge05.ui.ListScreen
import com.malakezzat.banquemisr.challenge05.ui.list.view.ListScreen
import com.malakezzat.banquemisr.challenge05.ui.list.viewmodel.ListScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.list.viewmodel.ListScreenViewModelFactory
import com.malakezzat.banquemisr.challenge05.ui.theme.MalakEzzatTaskTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private val repo by lazy {
        MoviesRepositoryImpl.getInstance(
            MoviesRemoteDataSourceImpl.
            getInstance( RetrofitHelper.getInstance().create(MovieApiService::class.java)))
    }
    private val listScreenViewModelFactory by lazy {
        ListScreenViewModelFactory(repo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MalakEzzatTaskTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ListScreen
                ) {
                    composable<ListScreen> {
                        val viewModel : ListScreenViewModel = viewModel(factory = listScreenViewModelFactory)
                        ListScreen(viewModel,navController)
                    }
//                    composable<DetailsScreen> {
//                        val args = it.toRoute<DetailsScreen>()
//                        DetailsScreen(args.movieId)
//                    }
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

