package com.example.todoapp.feature_task.view.task

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.feature_task.domain.model.Task

/**
 * lazy column item
 * @author zyzz
 * @param task The task for reminder
 * @param modifier Modifier
 * @param onClick Call back lambda that returns task when clicked
 */
@Composable
fun TaskItem(
    task: Task ,
    modifier: Modifier=Modifier,
    onClick: (Task) -> Unit
)
{
    Card(
        elevation = 5.dp,
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(5.dp)
            .animateContentSize(
                animationSpec = spring(
                    Spring.DampingRatioHighBouncy ,
                    Spring.StiffnessLow
                )
            ) ,
        backgroundColor = MaterialTheme.colorScheme.secondary,
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement=Arrangement.Center
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground ,
                        modifier = Modifier ,
                        text = task.task ,
                        fontSize = 18.sp
                    )
                    if(task.isScheduled) {
                        Text(
                            text = task.scheduledDate?:"NONE",
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(top=5.dp) ,
                            fontSize = 14.sp
                        )
                    }
                }
                RadioButton(
                    modifier = Modifier ,
                    selected = false ,
                    onClick = {
                        onClick(task)
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }

}
