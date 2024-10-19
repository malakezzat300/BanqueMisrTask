package com.malakezzat.banquemisr.challenge05.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.malakezzat.banquemisr.challenge05.ui.list.view.ListScreen
import com.malakezzat.banquemisr.challenge05.ui.list.viewmodel.ListScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.theme.AppColors

@Composable
fun MovieTabs(viewModel: ListScreenViewModel, navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Now Playing", "Popular", "Upcoming")
    val icons = listOf(Icons.Filled.Movie, Icons.Filled.Star, Icons.Filled.Upcoming)

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabIndicator(tabPositions = tabPositions, selectedTabIndex = selectedTabIndex)
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = selectedTabIndex == index
                val tabColor by animateColorAsState(if (selected) AppColors.LavenderDark else Color.Gray,
                    label = "animate"
                )

                Tab(
                    selected = selected,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = title,
                            tint = tabColor
                        )
                    },
                    text = {
                        Text(
                            text = title,
                            color = tabColor,
                            fontSize = 12.sp
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> ListScreen(viewModel,navController)
            1 -> PopularScreen()
            2 -> UpcomingScreen()
        }
    }
}

@Composable
fun TabIndicator(tabPositions: List<TabPosition>, selectedTabIndex: Int) {
    val tabPosition = tabPositions[selectedTabIndex]
    val color = AppColors.Lavender

    TabRowDefaults.Indicator(
        Modifier.tabIndicatorOffset(tabPosition),
        height = 4.dp,
        color = color,
    )
}

@Composable
fun PopularScreen() {
    Text("Popular Movies", modifier = Modifier.padding(16.dp))
}

@Composable
fun UpcomingScreen() {
    Text("Upcoming Movies", modifier = Modifier.padding(16.dp))
}