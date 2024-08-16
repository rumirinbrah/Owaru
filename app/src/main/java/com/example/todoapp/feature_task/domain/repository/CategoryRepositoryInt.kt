package com.example.todoapp.feature_task.domain.repository

import com.example.todoapp.feature_task.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepositoryInt {

    fun getAllCategories():Flow<List<Category>>

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}