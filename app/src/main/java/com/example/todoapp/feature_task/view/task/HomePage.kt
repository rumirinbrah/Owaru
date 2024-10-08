package com.example.todoapp.feature_task.view.task

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.todoapp.feature_task.domain.model.Category
import com.example.todoapp.feature_task.view.category.CategoryViewModel
import com.example.todoapp.ui.theme.sheetColor
import kotlinx.coroutines.launch

@OptIn(
     ExperimentalMaterialApi::class ,
    ExperimentalFoundationApi::class
)
@Composable
fun HomePage(
    taskViewModel: TaskViewModel ,
    categoryViewModel: CategoryViewModel
) {

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden ,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val localConfig = LocalConfiguration.current
    val focusManager = LocalFocusManager.current

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {}

    val granted = ContextCompat.checkSelfPermission(
        context ,
        Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED


    val tasks = taskViewModel.tasks.value


    val categories = categoryViewModel.categories.value
    var currentCategory by remember { mutableStateOf<Category?>(null) }


    ModalBottomSheetLayout(

        sheetBackgroundColor = sheetColor,
        sheetShape = RoundedCornerShape(topStartPercent = 12 , topEndPercent = 12) ,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddTaskSheet(
                    taskViewModel = taskViewModel ,
                    categoryViewModel ,
                    onDone = {
                        scope.launch {
                            sheetState.hide()
                            focusManager.clearFocus()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))
            }
        } ,
        sheetState = sheetState
    ) {


        Scaffold(
            backgroundColor = MaterialTheme.colorScheme.background ,
            scaffoldState = scaffoldState ,
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF7C0A8F) , CutCornerShape(4.dp))
                        .clickable {
                            scope.launch {
                                sheetState.show()
                            }
                        }
                )
                {
                    Icon(
                        modifier = Modifier.padding(vertical = 10.dp , horizontal = 15.dp) ,
                        imageVector = Icons.Default.Add ,
                        contentDescription = "add task",
                        tint = Color.White
                    )
                }
            } ,
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Column(
                Modifier
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {

                if (!granted) {
                    SideEffect {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
                LazyRow(
                    Modifier.padding(10.dp)
                ) {

                    items(
                        items = categories ?: emptyList() ,
                        key = { category->
                            category.id ?: 0
                        }
                    ) { category ->
                        CategoryItem(
                            item = category ,
                            selectedCategoryId = currentCategory?.id ?: -1 ,
                            onClick = {
                                if (currentCategory == it) {
                                    currentCategory = null
                                } else {
                                    currentCategory = it
                                }
                            }
                        )
                    }
                }
                if (tasks.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize() ,
                        verticalArrangement = Arrangement.Center ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            modifier = Modifier.size(localConfig.screenWidthDp.dp / 4) ,
                            imageVector = Icons.Default.ThumbUp ,
                            contentDescription = null ,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Nothing Here" ,
                            fontSize = 18.sp ,
                            fontWeight = FontWeight.Bold ,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                } else {
                    LazyColumn(
                        Modifier.padding(10.dp)
                    ) {
                        items(
                            tasks?.filter {
                                if (currentCategory == null) it.task.isNotEmpty() else it.category == currentCategory!!.name
                            } ?: emptyList(),
                            key = {
                                it.id ?:-1
                            }
                        ) { task ->

                            TaskItem(
                                task = task ,
                                modifier = Modifier.animateItemPlacement() ,
                                onClick = {

                                    taskViewModel.deleteTask(it)
                                    scope.launch {
                                        val result =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                "${it.task} completed" ,
                                                actionLabel = "UNDO"
                                            )
                                        when (result) {
                                            SnackbarResult.ActionPerformed -> {
                                                taskViewModel.restoreTask()
                                            }

                                            SnackbarResult.Dismissed -> {

                                                taskViewModel.resetDeleted()
                                            }
                                        }
                                    }
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}



