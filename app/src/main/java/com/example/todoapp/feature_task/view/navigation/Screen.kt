package com.example.todoapp.feature_task.view.navigation

sealed class Screen(val route  :String) {

    object HomeScreen : Screen("home_screen")
    object CategoryScreen : Screen("category_screen")

    object CategoryTasksScreen : Screen("cat_tasks_screen")

}