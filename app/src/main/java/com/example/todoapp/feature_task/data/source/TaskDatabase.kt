package com.example.todoapp.feature_task.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.feature_task.domain.model.Category
import com.example.todoapp.feature_task.domain.model.Task


@Database(
    entities = [Task::class,Category::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase :RoomDatabase(){

    abstract val taskDao : TaskDao

    companion object{
        const val DB_NAME ="task_db"

    }

}