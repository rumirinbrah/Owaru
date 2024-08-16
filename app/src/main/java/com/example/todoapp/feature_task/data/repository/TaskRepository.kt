package com.example.todoapp.feature_task.data.repository

import com.example.todoapp.feature_task.data.source.TaskDao
import com.example.todoapp.feature_task.domain.model.Task
import com.example.todoapp.feature_task.domain.repository.TaskRepositoryInt
import kotlinx.coroutines.flow.Flow

class TaskRepository (

    private val taskDao: TaskDao

): TaskRepositoryInt {
    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }

    override suspend fun getTaskById(id: Long): Task {
        return taskDao.getTaskById(id)
    }

    override suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}