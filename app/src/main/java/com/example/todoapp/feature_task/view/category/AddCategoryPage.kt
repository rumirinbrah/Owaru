package com.example.todoapp.feature_task.view.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todoapp.feature_task.view.navigation.Screen
import com.example.todoapp.feature_task.view.task.TaskViewModel
import com.example.todoapp.ui.theme.sheetColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class , ExperimentalMaterialApi::class)
@Composable
fun AddCategoryPage(
    categoryViewModel: CategoryViewModel,
    taskViewModel: TaskViewModel,
    navController : NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden ,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    var showDialog by remember{ mutableStateOf(false) }

    val categories =categoryViewModel.categories.value


    ModalBottomSheetLayout(
        sheetBackgroundColor = sheetColor,
        sheetShape = RoundedCornerShape(topStartPercent = 12 , topEndPercent = 12) ,
        sheetState = sheetState,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddCategorySheet(
                    categoryViewModel ,
                    onDone = {
                        scope.launch {
                            sheetState.hide()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))
            }
        }
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
                                if (categoryViewModel.getSize() > 9) {
                                    showDialog = true
                                } else {
                                    sheetState.show()
                                }
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
                Modifier.padding(it)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(10.dp),
                    columns = GridCells.Fixed(2)
                ){
                    items(
                        items = categories ?: emptyList(),
                        key = {
                            it.id ?: 0
                        }
                    ){category->
                        CategoryItem(
                            category = category ,
                            deleteCategory = {
                                categoryViewModel.deleteCategory(it)
                            },
                            onClick={
                                taskViewModel.getTasksByCategory(category.name)
                                navController.navigate(Screen.CategoryTasksScreen.route)
                            }
                        )
                    }
                }

            }
            if(showDialog) {
                AlertDialog(
                    backgroundColor = Color.DarkGray,
                    onDismissRequest = { showDialog = false } ,
                    text = {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){

                            Text(
                                text = "Oops you cannot create more than 10 " +
                                        "categories!! Please remove existing ones to add more",
                                textAlign = TextAlign.Center
                            )
                            Button(onClick = { showDialog=false }) {
                                Text(text = "Okay")
                            }
                        }
                    } ,
                    buttons = {}
                )
            }

        }
    }
}