package com.bashkir.documentstasks.viewmodels

import android.content.Context
import android.net.Uri
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.services.TasksService
import com.bashkir.documentstasks.ui.components.filters.TaskFilterOption
import com.bashkir.documentstasks.ui.components.filters.TaskFilterOption.*
import com.bashkir.documentstasks.utils.filter
import com.bashkir.documentstasks.utils.getAllFromUri
import com.bashkir.documentstasks.utils.getExtension
import com.bashkir.documentstasks.utils.writeDocument
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.time.LocalDateTime

class TasksViewModel(
    initialState: TasksState,
    private val service: TasksService,
    private val context: Context
) :
    MavericksViewModel<TasksState>(initialState) {
    var myId = ""

    fun onCreate() {
        getAllTasks()
        getAllUsers()
        myId = service.getMyId()
    }

    fun getAllTasks() = suspend {
        service.getAllTasks()
    }.execute(retainValue = TasksState::tasks) { copy(tasks = it) }

    fun getAllUsers() = suspend {
        service.getAllUsers()
    }.execute { copy(users = it) }

    fun filterTasks(
        tasks: List<Task>,
        searchText: String,
        filterOption: TaskFilterOption
    ): List<Task> =
        when (filterOption) {
            ALL -> tasks
            MY -> tasks.filter { it.author.id != myId }
            ISSUED -> tasks.getIssued()
            COMPLETED -> tasks.getIssued()
                .filter { task -> task.performs.all { it.status == PerformStatus.Completed } }
            FAILED -> tasks.getIssued()
                .filter { task ->
                    task.deadline.isBefore(LocalDateTime.now()) &&
                            task.performs.any { it.status != PerformStatus.Completed }
                }
        }.filter(searchText)

    private fun List<Task>.getIssued(): List<Task> = filter { it.author.id == myId }

    fun getMyPerform(task: Task): Perform = service.getMyPerform(task)

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

    fun addDocumentToTask(task: Task, uri: Uri?) = uri?.let {
        getAllFromUri(uri, context) { name, _, bytes ->
            suspend {
                service.addDocumentToTask(
                    task,
                    DocumentForm(name!!, bytes!!, name.getExtension(), null, listOf(), listOf())
                )
            }.executeWithTaskUpdate()
        }
    }

    fun downloadDocument(document: Document, uri: Uri?) = writeDocument(document, uri, context)

    private fun (suspend () -> Unit).executeWithTaskUpdate() = execute {
        if (it is Success) getAllTasks()
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