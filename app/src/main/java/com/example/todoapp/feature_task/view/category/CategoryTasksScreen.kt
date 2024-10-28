package com.example.todoapp.feature_task.view.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.feature_task.view.task.TaskItem
import com.example.todoapp.feature_task.view.task.TaskViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryTasksScreen(
    taskViewModel: TaskViewModel
) {

    val tasks = taskViewModel.categoryTasks.value


    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,

    ) {
        Column(
            Modifier.padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                Modifier.padding(10.dp)
            ) {
                items(
                    tasks ?: emptyList() ,
                    key = {
                        it.id ?: -1
                    }
                ) { task ->

                    TaskItem(
                        task = task ,
                        modifier = Modifier.animateItemPlacement() ,
                        onClick = {


                        }
                    )

                }
            }
        }
    }


}