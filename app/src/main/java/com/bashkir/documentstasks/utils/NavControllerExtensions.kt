package com.bashkir.documentstasks.utils

import androidx.navigation.NavController
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.navigation.MainGraphs

fun NavController.navigate(task: Task) =
    navigate(
        MainGraphs.MainGraph.TaskDetail.destinationWithArgument(
            task.id.toString()
        )
    )