package com.bashkir.documentstasks.ui.sreens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.bashkir.documentstasks.contracts.GoogleApiContract
import com.bashkir.documentstasks.ui.components.LoadingScreen
import com.bashkir.documentstasks.ui.components.buttons.SignInGoogleButton
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.authNavigate
import com.bashkir.documentstasks.viewmodels.AuthState
import com.bashkir.documentstasks.viewmodels.AuthViewModel

@Composable
fun AuthScreenBody(viewModel: AuthViewModel, navController: NavController) =
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val signInRequestCode = 1
        val userId by viewModel.collectAsState(AuthState::userId)
        val (button, errorText, label) = createRefs()

        LaunchedEffect(true) {
            viewModel.checkSignedIn()
        }

        LaunchedEffect(userId) {
            if (userId is Success) {
                viewModel.setUninitialized()
                navController.authNavigate()
            }
        }

        val authResultLauncher =
            rememberLauncherForActivityResult(
                contract = GoogleApiContract(),
                onResult = viewModel::onSignInResult
            )

        if (userId is Loading)
            LoadingScreen()
        else {
            if (!isSystemInDarkTheme())
                Text(
                    "Приложение создано под темную тему. Настоятельно рекомендуем попробовать.",
                    color = Color.Black,
                    modifier = Modifier
                        .constrainAs(label) {
                            top.linkTo(parent.top)
                        }
                        .padding(10.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            if (userId is Fail)
                ErrorText(ref = errorText, authButton = button, fail = userId as Fail)

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

@Composable
private fun ConstraintLayoutScope.ErrorText(
    ref: ConstrainedLayoutReference,
    authButton: ConstrainedLayoutReference,
    fail: Fail<*>
) = Text(
    "Авторизация не удалась: " +
            "${
                if (fail.error.message == null)
                    "Авторизация возможна только через корпоративную почту сотрудника школы."
                else fail.error.message
            }",
    style = titleText,
    color = Color.Red,
    modifier = Modifier
        .fillMaxWidth()
        .constrainAs(ref) { bottom.linkTo(authButton.top) },
    textAlign = TextAlign.Center
)