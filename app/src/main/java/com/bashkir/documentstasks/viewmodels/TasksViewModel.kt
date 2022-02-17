package com.bashkir.documentstasks.viewmodels

import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.TaskForm
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.services.TasksService
import com.bashkir.documentstasks.ui.components.cards.TaskFilterOption
import com.bashkir.documentstasks.utils.filter
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TasksViewModel(initialState: TasksState, private val service: TasksService) :
    MavericksViewModel<TasksState>(initialState) {

    fun getAllTasks() = suspend {
        service.getAllTasks()
    }.execute { copy(tasks = it) }

    fun filterTasks(
        tasks: List<Task>,
        searchText: String,
        filterOption: TaskFilterOption
    ): List<Task> =
        when (filterOption) {
            TaskFilterOption.ALL -> tasks
            TaskFilterOption.MY -> service.onlyMyTasks(tasks)
            TaskFilterOption.ISSUED -> service.onlyIssuedTasks(tasks)
        }.filter(searchText)

    fun addTask(task: TaskForm) = suspend {
        service.addTask(task)
    }.execute {
        getAllTasks()
        copy()
    }

    fun getAllUsers() = suspend {
        service.getAllUsers()
    }.execute { copy(users = it) }

    fun completeTask(task: Task) = suspend {
        service.completeTask(task)
    }

    companion object : MavericksViewModelFactory<TasksViewModel, TasksState>, KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: TasksState
        ): TasksViewModel = get { parametersOf(state) }
    }
}

data class TasksState(val tasks: Async<List<Task>> = Uninitialized, val users: Async<List<User>> = Uninitialized) : MavericksState