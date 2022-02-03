package com.bashkir.documentstasks.ui.sreens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.NavController
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.google.GoogleApiContract
import com.bashkir.documentstasks.ui.components.LoadingScreen
import com.bashkir.documentstasks.ui.components.SignInGoogleButton
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.authNavigate
import com.bashkir.documentstasks.viewmodels.AuthState
import com.bashkir.documentstasks.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthScreenBody(viewModel: AuthViewModel, navController: NavController) =
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val signInRequestCode = 1
        val user by viewModel.collectAsState(AuthState::user)
        val (button, errorText) = createRefs()

        LaunchedEffect(true){
            viewModel.checkSignedIn()?.let{
                navController.authNavigate(it)
            }
        }

        val authResultLauncher =
            rememberLauncherForActivityResult(contract = GoogleApiContract()) {
                onSignInResult(
                    it,
                    viewModel
                )
            }

        if (user is Loading)
            LoadingScreen()
        else {
            if (user is Fail)
                ErrorText(ref = errorText, authButton = button, fail = user as Fail)

            AuthButton(ref = button) {
                viewModel.setLoading()
                authResultLauncher.launch(signInRequestCode)
            }
        }
    }

@Composable
private fun ConstraintLayoutScope.AuthButton(
    ref: ConstrainedLayoutReference,
    onClick: () -> Unit
) = SignInGoogleButton(
    modifier = Modifier
        .constrainAs(ref) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        .padding(bottom = 32.dp),
    onClick = onClick
)

private fun onSignInResult(task: Task<GoogleSignInAccount>?, viewModel: AuthViewModel) {
    try {
        val account = task?.getResult(ApiException::class.java)
        if (account != null) {
            viewModel.setUser(account)
        } else {
            viewModel.setFailed()
        }
    } catch (e: ApiException) {
        viewModel.setFailed(e)
    }
}

@Composable
private fun ConstraintLayoutScope.ErrorText(
    ref: ConstrainedLayoutReference,
    authButton: ConstrainedLayoutReference,
    fail: Fail<User>
) = Text(
    "Авторизация неудалась ${fail.error.message?.let { "($it)" }}",
    style = titleText,
    color = Color.Red,
    modifier = Modifier
        .fillMaxWidth()
        .constrainAs(ref) { bottom.linkTo(authButton.top) },
    textAlign = TextAlign.Center
)