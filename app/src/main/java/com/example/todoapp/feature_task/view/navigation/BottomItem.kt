package com.example.todoapp.feature_task.view.navigation

import androidx.annotation.DrawableRes
import com.example.todoapp.R

data class BottomItem(
    @DrawableRes val icon : Int,
    val title : String,
    val route : String
)
val bottomItems = listOf(
    BottomItem(R.drawable.home_icon,"Home",Screen.HomeScreen.route),
    BottomItem(R.drawable.bookmark_icon,"Category",Screen.CategoryScreen.route)
)
