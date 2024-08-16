package com.example.todoapp.feature_task.view.category

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.feature_task.data.repository.CategoryRepository
import com.example.todoapp.feature_task.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

    private val categoryRepository: CategoryRepository

) : ViewModel() {

    private val _categories = mutableStateOf<List<Category>?>(null)
    val categories: MutableState<List<Category>?> get() = _categories

    private var job: Job? = null

    init {
        getCategories()
    }

    private fun getCategories() {

        categoryRepository.getAllCategories()
            .onEach {
                _categories.value = it
            }.launchIn(viewModelScope)

    }
    fun getSize():Int{
        return _categories.value?.size ?: -1
    }

    fun deleteCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.deleteCategory(category = category)
        }
    }

    fun addCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.addCategory(category = category)
        }
    }

}