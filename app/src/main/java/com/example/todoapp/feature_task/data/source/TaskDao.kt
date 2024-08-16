package com.example.todoapp.feature_task.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addTask(task: Task)

    @Delete
    abstract suspend fun deleteTask(task: Task)

    //ALL
    @Query("select * from task_table")
    abstract fun getTasks() : Flow<List<Task>>

    //SINGLE
    @Query("select * from task_table where id = :id")
    abstract suspend fun getTaskById(id:Long) : Task

}