package com.malakezzat.banquemisr.challenge05.ui.main.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.view.NowPlayingScreen
import com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel.NowPlayingScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.popular.view.PopularScreen
import com.malakezzat.banquemisr.challenge05.ui.lists.popular.viewmodel.PopularScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.view.UpcomingScreen
import com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.viewmodel.UpcomingScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.theme.AppColors

@Composable
fun MovieTabs(nowPlayingScreenViewModel: NowPlayingScreenViewModel,
              popularScreenViewModel: PopularScreenViewModel,
              upcomingScreenViewModel: UpcomingScreenViewModel,
              navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Now Playing", "Popular", "Upcoming")
    val icons = listOf(Icons.Filled.Movie, Icons.Filled.Star, Icons.Filled.Upcoming)

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            backgroundColor = Color.White,
            contentColor = AppColors.Rose
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = selectedTabIndex == index
                val tabColor by animateColorAsState(
                    targetValue = if (selected) AppColors.RoseDark else Color.Gray,
                    label = "tabColorAnimation"
                )
                val iconSize by animateDpAsState(
                    targetValue = if (selected) 28.dp else 24.dp,
                    label = "iconSizeAnimation"
                )

                Tab(
                    selected = selected,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = {
                        Text(
                            text = title,
                            color = tabColor,
                            fontSize = 12.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = title,
                            modifier = Modifier.size(iconSize),
                            tint = tabColor
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> NowPlayingScreen(nowPlayingScreenViewModel, navController)
            1 -> PopularScreen(popularScreenViewModel, navController)
            2 -> UpcomingScreen(upcomingScreenViewModel, navController)
        }
    }
}

