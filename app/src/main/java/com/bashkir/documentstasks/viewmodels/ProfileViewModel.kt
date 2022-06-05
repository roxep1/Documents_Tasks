package com.bashkir.documentstasks.viewmodels

import android.content.Context
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.services.ProfileService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class ProfileViewModel(
    initialState: ProfileState,
    private val context: Context,
    private val service: ProfileService
) : MavericksViewModel<ProfileState>(initialState) {

    fun logout() = suspend {
        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
        service.logout()
    }.execute { copy(logoutState = it) }

    fun clearLogoutState() = setState { copy(logoutState = Uninitialized) }

    fun getAuthorizedUser() = suspend { service.getAuthorizedUser() }.execute { copy(user = it) }

    companion object : MavericksViewModelFactory<ProfileViewModel, ProfileState>, KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: ProfileState
        ): ProfileViewModel = get { parametersOf(state) }
    }
}

data class ProfileState(
    val user: Async<User> = Uninitialized,
    val logoutState: Async<Unit> = Uninitialized
) : MavericksState