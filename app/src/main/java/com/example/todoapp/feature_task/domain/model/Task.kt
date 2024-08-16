package com.example.todoapp.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Long?=null,
    val task : String,
    val category : String,
    val isScheduled : Boolean =false,
    val notificationID : Int? =null,
    val scheduledDate : String? =null
)
