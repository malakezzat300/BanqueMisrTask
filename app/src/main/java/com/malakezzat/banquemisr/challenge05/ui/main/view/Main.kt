package com.malakezzat.banquemisr.challenge05.ui.main.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel.NowPlayingScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.popular.viewmodel.PopularScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.viewmodel.UpcomingScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(nowPlayingScreenViewModel: NowPlayingScreenViewModel,
                popularScreenViewModel: PopularScreenViewModel,
                upcomingScreenViewModel: UpcomingScreenViewModel,
                navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Movies") })
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)

            ) {
                MovieTabs(nowPlayingScreenViewModel,
                    popularScreenViewModel,
                    upcomingScreenViewModel,
                    navController)
            }
        }
    )
}