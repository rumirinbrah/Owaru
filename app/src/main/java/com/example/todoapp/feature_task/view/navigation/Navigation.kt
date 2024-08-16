package com.example.todoapp.feature_task.view.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navDeepLink
import com.example.todoapp.feature_task.domain.util.Constants.MY_URI
import com.example.todoapp.feature_task.view.category.AddCategoryPage
import com.example.todoapp.feature_task.view.category.CategoryViewModel
import com.example.todoapp.feature_task.view.task.HomePage
import com.example.todoapp.feature_task.view.task.TaskViewModel

@Composable
fun Navigation(
    navController : NavHostController,
    taskViewModel: TaskViewModel,
    categoryViewModel: CategoryViewModel
) {


    Scaffold(
        bottomBar = {
            CustomBottomBar(navController)
        }
    ) {

        NavHost(
            modifier = Modifier.padding(it),
            navController = navController ,
            startDestination = Screen.HomeScreen.route
        ){
            composable(
                route = Screen.HomeScreen.route ,
                deepLinks = listOf(
                    navDeepLink { uriPattern = MY_URI }
                )
            ){

                HomePage(
                    taskViewModel = taskViewModel ,
                    categoryViewModel = categoryViewModel
                )
            }
            composable(Screen.CategoryScreen.route){
                AddCategoryPage(categoryViewModel=categoryViewModel)
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
        tonalElevation = 2.dp
    ) {
        items.forEach { item->
            val selected = item.route==backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                          navController.navigate(item.route){
                              popUpTo(navController.graph.findStartDestination().id){
                                  saveState=true
                              }
                              launchSingleTop=true
                              restoreState=true
                          }
                } ,
                label = { Text(text = item.title)},
                icon = {
                    Icon(painter = painterResource(id = item.icon) , contentDescription =null )
                }
            )
        }
    }
}
