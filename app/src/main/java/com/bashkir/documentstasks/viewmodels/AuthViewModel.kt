package com.bashkir.documentstasks.viewmodels

import android.content.Context
import androidx.navigation.NavController
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.models.Username
import com.bashkir.documentstasks.utils.authNavigate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class AuthViewModel(initialState: AuthState, private val context: Context) :
    MavericksViewModel<AuthState>(initialState) {

    fun checkSignedIn() : User? {
        GoogleSignIn.getLastSignedInAccount(context)?.let {
            setUser(it)
            return it.toUser()
        }
        return null
    }

    fun setLoading() = setState { copy(user = Loading()) }

    fun setFailed(e: Throwable = Throwable()) = setState { copy(user = Fail(e)) }

    fun setUser(account: GoogleSignInAccount) = setState { copy(user = Success(account.toUser())) }

    private fun GoogleSignInAccount.toUser(): User =
        User(id!!, Username(givenName!!, familyName!!), email!!)

    companion object : MavericksViewModelFactory<AuthViewModel, AuthState>, KoinComponent {
        override fun create(viewModelContext: ViewModelContext, state: AuthState): AuthViewModel =
            get { parametersOf(state) }
    }
}


data class AuthState(
    val user: Async<User> = Uninitialized
) : MavericksState