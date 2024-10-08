package com.example.todoapp.feature_task.view.category

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.feature_task.domain.model.Category
import com.example.todoapp.feature_task.domain.model.categoryColors

@Composable
fun AddCategorySheet(
    categoryViewModel: CategoryViewModel,
    onDone:()->Unit
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var text by remember { mutableStateOf("") }
    val colors = categoryColors
    var currentColor by remember{ mutableStateOf(colors.random()) }


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
            keyboardOptions = KeyboardOptions(KeyboardCapitalization.Words) ,
            textStyle = TextStyle(
                fontSize = 18.sp
            ) ,
            placeholder = { Text(text = "Enter Category") } ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp) ,
            value = text ,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ) ,
            onValueChange = { text = it }
        )
        LazyRow(
            Modifier.padding(vertical = 5.dp)
        ){
            items(colors){
                ColorItem(color = it,currentColor, onClick = {currentColor=it})

            }
        }
        Button(
            colors= ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            enabled = text.trim().isNotEmpty() ,
            shape = RectangleShape ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            onClick = {
                if(!text.validLength()){
                    Toast.makeText(context , "Category name too long!" , Toast.LENGTH_SHORT).show()
                }else{
                    val category = Category(name = text.trim(), color = currentColor.toArgb())
                    categoryViewModel.addCategory(category)
                    onDone()
                    text=""
                }
            }
        ) {
            Text(text = "Done",color = MaterialTheme.colorScheme.onBackground)
        }
    }

}

@Composable
fun ColorItem(
    color: Color,
    currentColor: Color,
    onClick:()->Unit
) {
    val selected = currentColor==color
    Box(
        modifier = Modifier
            .size(50.dp)
            .padding(5.dp)
            .clip(CircleShape)
            .background(color)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        if(selected){
            Icon(imageVector = Icons.Default.Check , contentDescription =null,tint= Color.Black )
        }
    }
}

fun String.validLength():Boolean{
    return this.length<20
}


