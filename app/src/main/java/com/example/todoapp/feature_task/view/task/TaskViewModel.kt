package com.example.todoapp.feature_task.view.task

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.MyAlarmManager
import com.example.todoapp.feature_task.data.repository.TaskRepository
import com.example.todoapp.feature_task.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(

    private val taskRepository: TaskRepository ,
    private val context: Context

) : ViewModel() {

    private val alarmManager = MyAlarmManager(context)

    private val _tasks = mutableStateOf<List<Task>?>(null)
    val tasks: MutableState<List<Task>?> get() = _tasks

    private val _categoryTasks = mutableStateOf<List<Task>?>(null)
    val categoryTasks : MutableState<List<Task>?> get() = _categoryTasks

    private var deletedTask: Task? = null


    init {
        getTasks()
    }

    fun addTask(task: Task , time: Long?) {
        viewModelScope.launch {
            taskRepository.addTask(task = task)
            if (time != null) {
                alarmManager.scheduleAlarm(time , task.task , task.notificationID!!)
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task = task)
            deletedTask = task
        }
    }

    fun restoreTask() {
        viewModelScope.launch {
            if (deletedTask != null) {
                taskRepository.addTask(task = deletedTask!!)
            }
        }
    }

    fun resetDeleted() {
        if (deletedTask != null) {
            if (deletedTask!!.isScheduled) {
                alarmManager.cancelAlarm(deletedTask!!)
            }
            deletedTask = null
        }
    }


    private fun getTasks() {

        taskRepository.getTasks()
            .onEach {
                _tasks.value = it
            }.launchIn(viewModelScope)
    }

    fun getTasksByCategory(category: String) {

        taskRepository.getTasksByCategory(category)
            .onEach {
                _categoryTasks.value = it
            }.launchIn(viewModelScope)

    }


}