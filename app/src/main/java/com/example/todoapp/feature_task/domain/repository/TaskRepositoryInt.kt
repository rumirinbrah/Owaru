package com.example.todoapp.feature_task.domain.repository

import com.example.todoapp.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepositoryInt {

    fun getTasks() : Flow<List<Task>>

    suspend fun getTaskById(id:Long) : Task?

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)
}