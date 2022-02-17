package com.bashkir.documentstasks.utils

import androidx.navigation.NavController
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.navigation.Screen

fun NavController.navigate(task: Task) =
    navigate(
        Screen.TaskDetail.destinationWithArgument(
            task.id.toString()
        )
    )

fun NavController.authNavigate() {
    navigate(
        Screen.BottomNav.destination
    )
}