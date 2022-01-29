package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.test.testTasksList1
import com.bashkir.documentstasks.navigation.MainGraphs
import com.bashkir.documentstasks.ui.components.TaskCardList
import com.bashkir.documentstasks.ui.components.TopBarBottomNav
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun TasksScreenBody(navController: NavController) = Scaffold(topBar = {
    TopBarBottomNav(navController = navController)
}) {
    TaskCardList(onClick = {
        navController.navigate(
            MainGraphs.MainGraph.TaskDetail.destinationWithArgument(
                it.id.toString()
            )
        )
    }, tasks = testTasksList1)
}

@Preview
@Composable
private fun TasksScreenBodyPreview() = DocumentsTasksTheme {
    TasksScreenBody(navController = rememberNavController())
}