package com.bashkir.documentstasks.viewmodels

import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.PerformStatus
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.TaskForm
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.services.TasksService
import com.bashkir.documentstasks.ui.components.filters.FilterOption
import com.bashkir.documentstasks.ui.components.filters.TaskFilterOption
import com.bashkir.documentstasks.utils.filter
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TasksViewModel(initialState: TasksState, private val service: TasksService) :
    MavericksViewModel<TasksState>(initialState) {
    private var issuedTasks = listOf<Task>()

    init {
        onAsync(TasksState::tasks, onSuccess = {
            issuedTasks = service.onlyIssuedTasks(it)
        })
    }

    fun onCreate() {
        getAllTasks()
        getAllUsers()
    }

    fun getAllTasks() = suspend {
        service.getAllTasks()
    }.execute(retainValue = TasksState::tasks) { copy(tasks = it) }

    private fun getAllUsers() = suspend {
        service.getAllUsers()
    }.execute { copy(users = it) }

    fun filterTasks(
        tasks: List<Task>,
        searchText: String,
        filterOption: TaskFilterOption
    ): Map<Task, TaskFilterOption> =
        when (filterOption) {
            TaskFilterOption.ALL -> tasks.associateWith { if(issuedTasks.contains(it)) TaskFilterOption.ISSUED else TaskFilterOption.MY }
            TaskFilterOption.MY -> tasks.filter { !issuedTasks.contains(it) }
                .associateWith { TaskFilterOption.MY }
            TaskFilterOption.ISSUED -> issuedTasks.associateWith { TaskFilterOption.ISSUED }
            else -> tasks.associateWith { TaskFilterOption.COMPLETED }
        }.filter(searchText)

    fun isIssuedTask(task: Task): Boolean = issuedTasks.contains(task)

    fun getPerformStatus(task: Task): PerformStatus = service.getMyPerform(task).status

    fun addCommentToTask(task: Task, comment: String) = suspend {
        service.addCommentToTask(task, comment)
    }.executeWithTaskUpdate()

    fun addTask(task: TaskForm) = suspend {
        service.addTask(task)
    }.executeWithTaskUpdate()

    fun completeTask(task: Task) = suspend {
        service.completeTask(task)
    }.executeWithTaskUpdate()

    fun inProgressTask(task: Task) = suspend {
        service.inProgressTask(task)
    }.executeWithTaskUpdate()

    fun deleteTask(task: Task) = suspend {
        service.deleteTask(task)
    }.executeWithTaskUpdate()

    private fun (suspend () -> Unit).executeWithTaskUpdate() = execute {
        if(it is Success) getAllTasks()
        copy()
    }

    companion object : MavericksViewModelFactory<TasksViewModel, TasksState>, KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: TasksState
        ): TasksViewModel = get { parametersOf(state) }
    }
}

data class TasksState(
    val tasks: Async<List<Task>> = Uninitialized,
    val users: Async<List<User>> = Uninitialized
) : MavericksState