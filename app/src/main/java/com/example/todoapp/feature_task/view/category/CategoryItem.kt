package com.example.todoapp.feature_task.view.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.feature_task.domain.model.Category

@Composable
fun CategoryItem(
    category: Category,
    deleteCategory :(Category)->Unit
) {

    val config = LocalConfiguration.current
    val size = config.screenWidthDp.dp/3
    val deleteDialog = remember{ mutableStateOf(false) }

    Column(
        Modifier
            .size(size)
            .padding(5.dp)
            .background(MaterialTheme.colorScheme.onSecondary , RoundedCornerShape(25))
            .clickable {
                deleteDialog.value = true
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box (
            Modifier
                .background(Color(category.color) , CircleShape)
                .alpha(0.7f)
            
        ){
            Icon(
                modifier = Modifier.size(size / 2) ,
                painter = painterResource(id = R.drawable.category_icon) ,
                contentDescription = null
            )

        }
        Text(
            modifier = Modifier.padding(top=5.dp),
            text = category.name ,
            fontWeight = FontWeight.Bold
        )
    }
    if(deleteDialog.value){
        AlertDialog(
            backgroundColor = Color.DarkGray,
            onDismissRequest = { deleteDialog.value = false } ,
            buttons = {  },
            shape = RoundedCornerShape(25) ,
            text = {
                Column {
                    Text(text ="Delete category?" ,modifier=Modifier.align(Alignment.CenterHorizontally))
                    Row (
                        Modifier.fillMaxWidth()
                    ){
                        Button(
                            modifier = Modifier.weight(1f)
                                .padding(5.dp),
                            onClick = {
                                deleteCategory(category)
                                deleteDialog.value=false
                            }
                        ) {
                            Text(text = "Yes")
                        }

                        Button(
                            modifier = Modifier.weight(1f)
                                .padding(5.dp),
                            onClick = {deleteDialog.value=false }
                        ) {
                            Text(text = "No")
                        }
                    }
                }
            }

        )
    }
}

@Preview(showBackground = true)
@Composable
fun Tester() {
    CategoryItem(category = Category(name = "Daily"), deleteCategory = {})
}

