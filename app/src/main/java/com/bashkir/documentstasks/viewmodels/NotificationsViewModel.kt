package com.bashkir.documentstasks.viewmodels

import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.Notification
import com.bashkir.documentstasks.data.services.NotificationsService
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class NotificationsViewModel(
    initialState: NotificationsState,
    private val service: NotificationsService
) : MavericksViewModel<NotificationsState>(initialState) {
    init {
        loadNotifications()
    }

    private fun loadNotifications() =
        service.loadAllNotifications().execute { copy(notifications = it) }

    fun deleteNotification(notification: Notification) = suspend {
        service.deleteNotification(notification)
    }.execute { copy() }

    companion object : MavericksViewModelFactory<NotificationsViewModel, NotificationsState>,
        KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: NotificationsState
        ): NotificationsViewModel = get { parametersOf(state) }
    }
}

data class NotificationsState(val notifications: Async<List<Notification>> = Uninitialized) :
    MavericksState