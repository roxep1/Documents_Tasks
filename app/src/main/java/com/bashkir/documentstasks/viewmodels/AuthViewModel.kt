package com.bashkir.documentstasks.viewmodels

import android.content.Context
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.services.AuthService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class AuthViewModel(
    initialState: AuthState,
    private val context: Context,
    private val service: AuthService
) :
    MavericksViewModel<AuthState>(initialState) {

    fun checkSignedIn(): String? {
        GoogleSignIn.getLastSignedInAccount(context)?.let {
            setSignedUserId(it)
            return it.id
        }
        return null
    }

    fun setLoading() = setState { copy(userId = Loading()) }

    fun setFailed(e: Throwable = Throwable()) = setState { copy(userId = Fail(e)) }

    fun setSignedUserId(account: GoogleSignInAccount) = setSignedUserId(account.id!!)

    fun setSignedUserId(id: String) = suspend {
        service.authorizeUser(id)
    }.execute { copy(userId = it)  }

    companion object : MavericksViewModelFactory<AuthViewModel, AuthState>, KoinComponent {
        override fun create(viewModelContext: ViewModelContext, state: AuthState): AuthViewModel =
            get { parametersOf(state) }
    }
}


data class AuthState(
    val userId: Async<String> = Uninitialized
) : MavericksState