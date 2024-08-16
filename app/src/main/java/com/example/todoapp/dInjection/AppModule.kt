package com.example.todoapp.dInjection

import android.content.Context
import androidx.room.Room
import com.example.todoapp.feature_task.data.repository.CategoryRepository
import com.example.todoapp.feature_task.data.repository.TaskRepository
import com.example.todoapp.feature_task.data.source.CategoryDatabase
import com.example.todoapp.feature_task.data.source.TaskDao
import com.example.todoapp.feature_task.data.source.TaskDatabase
import com.example.todoapp.feature_task.domain.repository.TaskRepositoryInt
import com.example.todoapp.feature_task.view.category.CategoryViewModel
import com.example.todoapp.feature_task.view.task.TaskViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //TASK DB
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context):TaskDatabase{
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            TaskDatabase.DB_NAME
        ).build()
    }

    //CAT DB
    @Provides
    @Singleton
    fun provideCategoryDatabase(@ApplicationContext context:Context) : CategoryDatabase{
        return Room.databaseBuilder(
            context,
            CategoryDatabase::class.java,
            CategoryDatabase.CATEGORY_DB_NAME
        ).createFromAsset("database/category.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDatabase: TaskDatabase):TaskRepository{
        return TaskRepository(taskDatabase.taskDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDatabase: CategoryDatabase):CategoryRepository{
        return CategoryRepository(categoryDatabase.categoryDao)
    }


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context):Context{
        return context
    }

    @Provides
    @Singleton
    fun provideTaskViewModel(taskRepository: TaskRepository,context: Context):TaskViewModel{
        return TaskViewModel(taskRepository,context)
    }

    @Provides
    @Singleton
    fun provideCategoryViewModel(categoryRepository: CategoryRepository):CategoryViewModel{
        return CategoryViewModel(categoryRepository)
    }

}