package com.example.todoapp.feature_task.view.task

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.feature_task.domain.model.Category
import com.example.todoapp.feature_task.domain.model.Task
import com.example.todoapp.feature_task.view.category.CategoryViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun AddTaskSheet(
    taskViewModel: TaskViewModel ,
    categoryViewModel : CategoryViewModel ,
    onDone : ()->Unit
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    //ALARM VARS
    var pickedDate by remember { mutableStateOf<LocalDate?>(null) }
    var pickedTime by remember { mutableStateOf<LocalTime?>(null) }
    val formattedDate by remember {
        derivedStateOf {
            pickedDate?.let {
                DateTimeFormatter.ofPattern("dd MMM yy")
                    .format(pickedDate)
            }
        }
    }
    val dateDialog = rememberMaterialDialogState()
    val timeDialog = rememberMaterialDialogState()


    //states
    var text by remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf<Category?>(null) }

    //view-model
    val categories = categoryViewModel.categories.value


    Column(
        Modifier.padding(10.dp)
    ) {
        OutlinedTextField(
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White ,
                focusedIndicatorColor = Color.White ,
                unfocusedIndicatorColor = Color.Gray ,
                placeholderColor = Color.Gray
            ) ,
            keyboardOptions = KeyboardOptions(KeyboardCapitalization.Words),
            textStyle = TextStyle(
                fontSize = 18.sp
            ) ,
            placeholder = { Text(text = "Enter Task") } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = text ,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ) ,
            onValueChange = { text = it }
        )
        LazyRow(
            Modifier.padding(10.dp)
        ) {
            items(categories ?: emptyList()) { category ->
                CategoryItem(
                    item = category ,
                    selectedCategoryId = selectedCategory.value?.id ?:-1,
                    onClick = {
                        if( selectedCategory.value ==it){
                            selectedCategory.value=null
                        }else {
                            selectedCategory.value = it
                        }
                    }
                )
            }
        }
        Row(
            Modifier
                .padding(10.dp)
                .clickable {
                    dateDialog.show()
                } ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.alarm_icon) ,
                contentDescription = "add alarm" ,
                tint = Color.White
            )
            Text(
                modifier = Modifier.padding(start = 10.dp) ,
                text = if (pickedDate == null) "Reminder Off" else "Scheduled $formattedDate" ,
                color = Color.White
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            enabled = text.trim().isNotEmpty() ,
            shape = RectangleShape ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp) ,
            onClick = {

                if(pickedDate!=null)
                {
                    val id = System.currentTimeMillis().toInt()
                    val task = Task(
                        task = text.trim() ,
                        category = selectedCategory.value?.name ?:"None" ,
                        isScheduled = true ,
                        notificationID = id,
                        scheduledDate = formattedDate
                    )
                    val time = getTime(pickedDate!!,pickedTime!!)
                    taskViewModel.addTask(task,time)
                }else {
                    val task = Task(
                        task = text.trim() ,
                        category = selectedCategory.value?.name ?: "None"
                    )
                    taskViewModel.addTask(task , null)
                }
                onDone()
                text=""
                selectedCategory.value=null
                pickedTime=null
                pickedDate=null
            }
        ) {
            androidx.compose.material3.Text(text = "Done", color = MaterialTheme.colorScheme.onBackground)
        }
    }

    MaterialDialog(
        dialogState = dateDialog ,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now() ,
            title = "Pick Date",
        ) {
            pickedDate = it
            timeDialog.show()
        }
    }
    MaterialDialog(
        dialogState = timeDialog ,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.now() ,
            title = "Pick Time"
        ) {
            pickedTime = it
        }
    }
}

fun getTime( pickedDate: LocalDate , pickedTime: LocalTime):Long {

        val minute = pickedTime.minute
        val hour = pickedTime.hour
        val day = pickedDate.dayOfMonth
        val month = pickedDate.monthValue-1
        val year = pickedDate.year
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day,hour,minute)
        return  calendar.timeInMillis
}

@Composable
fun CategoryItem(
    item: Category ,
    selectedCategoryId: Long ,
    onClick: (Category) -> Unit
) {
    val selected = selectedCategoryId==item.id
    val color : Color by animateColorAsState(
        targetValue = if(selected) Color(item.color) else Color.White,
        label = "",
        animationSpec = tween(600)
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(25))
            .background(if (selected) color else MaterialTheme.colorScheme.tertiary)
            .clickable {
                onClick(item)
            }
    ) {
        Text(
            text = item.name ,
            color = if(selected) Color.Black else Color.White ,
            modifier = Modifier.padding(vertical = 8.dp , horizontal = 20.dp),
            fontSize = 15.sp
        )

    }
}


