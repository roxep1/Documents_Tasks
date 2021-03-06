package com.bashkir.documentstasks.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bashkir.documentstasks.R

@Composable
fun SignInGoogleButton(
    modifier : Modifier = Modifier,
    onClick: () -> Unit
) = Surface(
    modifier = modifier
        .clickable(
            onClick = onClick
        ),
    border = BorderStroke(width = 1.dp, color = Color.LightGray),
    color = MaterialTheme.colors.surface
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(
            start = 12.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 12.dp
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Login",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Sign in With Google"
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}


@Preview
@Composable
fun PreviewSignInButton() {
    SignInGoogleButton {}
}