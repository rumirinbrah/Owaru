package com.example.todoapp.feature_task.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.todoapp.feature_task.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addCategory(category: Category)

    @Delete
    abstract suspend fun deleteCategory(category: Category)

    @Query("select * from category_table")
    abstract fun getAllCategories():Flow<List<Category>>

}