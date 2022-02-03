package com.bashkir.documentstasks.utils

import androidx.navigation.NavController
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.navigation.MainNavGraphs

fun NavController.navigate(task: Task) =
    navigate(
        MainNavGraphs.MainGraph.TaskDetail.destinationWithArgument(
            task.id.toString()
        )
    )

fun NavController.authNavigate(user: User) {
    navigate(
        MainNavGraphs.MainGraph.BottomNav.destinationWithArgument(
            user.id
        )
    )
}