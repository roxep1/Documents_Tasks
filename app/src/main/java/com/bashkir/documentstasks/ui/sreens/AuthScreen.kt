package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.bashkir.documentstasks.ui.components.SignInGoogleButton

@Composable
fun AuthScreenBody(onAuthSuccess: () -> Unit) =
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val authButton = createRef()
        AuthButton(ref = authButton, onAuthSuccess)
    }

@Composable
private fun ConstraintLayoutScope.AuthButton(ref: ConstrainedLayoutReference, onAuthSuccess: () -> kotlin.Unit) =
    Box(
        modifier = Modifier
            .constrainAs(ref) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(0.dp, 32.dp)
    ) {
        SignInGoogleButton{
            //TODO()
            onAuthSuccess()
        }
    }

@Preview
@Composable
private fun AuthScreenPreview() {
    AuthScreenBody{}
}