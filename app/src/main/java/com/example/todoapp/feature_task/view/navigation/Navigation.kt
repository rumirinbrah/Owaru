package com.example.todoapp.feature_task.view.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navDeepLink
import com.example.todoapp.feature_task.domain.util.Constants.MY_URI
import com.example.todoapp.feature_task.view.category.AddCategoryPage
import com.example.todoapp.feature_task.view.category.CategoryTasksScreen
import com.example.todoapp.feature_task.view.category.CategoryViewModel
import com.example.todoapp.feature_task.view.task.HomePage
import com.example.todoapp.feature_task.view.task.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController ,
    taskViewModel: TaskViewModel ,
    categoryViewModel: CategoryViewModel
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Row(
                    Modifier.fillMaxWidth()
                        ,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "OWARU" ,
                        fontSize = 25.sp ,
                        color = MaterialTheme.colorScheme.outline ,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
                modifier = Modifier.shadow(5.dp)
            )

        } ,
        bottomBar = {
            CustomBottomBar(navController)
        }
    ) {

        NavHost(
            modifier = Modifier.padding(it) ,
            navController = navController ,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(
                route = Screen.HomeScreen.route ,
                deepLinks = listOf(
                    navDeepLink { uriPattern = MY_URI }
                )
            ) {

                HomePage(
                    taskViewModel = taskViewModel ,
                    categoryViewModel = categoryViewModel
                )
            }
            composable(Screen.CategoryScreen.route) {
                AddCategoryPage(
                    categoryViewModel = categoryViewModel ,
                    taskViewModel=taskViewModel,
                    navController = navController
                )
            }
            composable(Screen.CategoryTasksScreen.route){
                
                CategoryTasksScreen(taskViewModel=taskViewModel)
            }
        }
    }

}


@Composable
fun CustomBottomBar(
    navController: NavHostController
) {

    val items = bottomItems
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomAppBar(
        tonalElevation = 10.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected ,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                } ,
                label = { Text(text = item.title) } ,
                icon = {
                    Icon(painter = painterResource(id = item.icon) , contentDescription = null)
                }
            )
        }
    }
}
