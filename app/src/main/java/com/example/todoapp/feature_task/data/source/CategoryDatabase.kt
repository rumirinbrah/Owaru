package com.example.todoapp.feature_task.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.feature_task.domain.model.Category

@Database(
    entities = [Category::class],
    version = 1,
    exportSchema = false
)
abstract class CategoryDatabase :RoomDatabase(){

    abstract val categoryDao : CategoryDao
    companion object{
        const val CATEGORY_DB_NAME ="category_db"
    }


}