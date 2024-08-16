package com.example.todoapp.feature_task.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id : Long?=null,
    val name : String="None",
    val color : Int = Color.Blue.toArgb()
)

val categoryColors = listOf(
    Color(0xFF9CE668),
    Color(0xFFE6E468) ,
    Color(0xFFEC9066) ,
    Color(0xFF68E6D7) ,
    Color(0xFFA768E6) ,
    Color(0xFFE6688C) ,
)

