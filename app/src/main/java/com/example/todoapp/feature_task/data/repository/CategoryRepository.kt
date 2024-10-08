package com.example.todoapp.feature_task.data.repository

import com.example.todoapp.feature_task.data.source.CategoryDao
import com.example.todoapp.feature_task.domain.model.Category
import com.example.todoapp.feature_task.domain.repository.CategoryRepositoryInt
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
    private val categoryDao: CategoryDao
) :CategoryRepositoryInt
{
    //get
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    //get by category



    //add
    override suspend fun addCategory(category: Category) {
        categoryDao.addCategory(category)
    }

    //delete
    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
}