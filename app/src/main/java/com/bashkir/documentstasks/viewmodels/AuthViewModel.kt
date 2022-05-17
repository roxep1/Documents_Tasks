package com.bashkir.documentstasks.viewmodels

import android.content.Context
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.services.AuthService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class AuthViewModel(
    initialState: AuthState,
    private val context: Context,
    private val service: AuthService
) :
    MavericksViewModel<AuthState>(initialState) {

    fun checkSignedIn() =
        GoogleSignIn.getLastSignedInAccount(context)?.let {
            setSignedUser(it)
        }

    fun setLoading() = setState { copy(userId = Loading()) }

    private fun setFailed(e: Throwable = Throwable()) = setState { copy(userId = Fail(e)) }

    fun setUninitialized() = setState { copy(userId = Uninitialized) }

    private fun setSignedUser(account: GoogleSignInAccount) =
        setSignedUser(account.id!!, account.idToken!!)

    fun onSignInResult(task: Task<GoogleSignInAccount>?) =
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account != null)
                setSignedUser(account)
            else setFailed()
        } catch (e: ApiException) {
            setFailed(e)
        }

    private fun setSignedUser(id: String, idToken: String) = suspend {
        service.authorizeUser(id, idToken)
    }.execute { copy(userId = it) }

    companion object : MavericksViewModelFactory<AuthViewModel, AuthState>, KoinComponent {
        override fun create(viewModelContext: ViewModelContext, state: AuthState): AuthViewModel =
            get { parametersOf(state) }
    }
}


data class AuthState(
    val userId: Async<String> = Uninitialized
) : MavericksState