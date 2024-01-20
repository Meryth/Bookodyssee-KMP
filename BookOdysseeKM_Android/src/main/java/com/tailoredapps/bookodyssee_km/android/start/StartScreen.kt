package com.tailoredapps.bookodyssee_km.android.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tailoredapps.bookodyssee_km.android.R
import com.tailoredapps.bookodyssee_km.android.base_ui.PrimaryButton

@Composable
fun StartScreen(
    onLoginClick: () -> Unit
) {
    StartScreenView(
        onLoginClick = onLoginClick,
        onRegisterClick = {}
    )
}

@Composable
fun StartScreenView(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo_welcome_book),
                contentDescription = null,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Text(
                text = stringResource(R.string.welcome_subtitle),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            PrimaryButton(
                btnText = stringResource(R.string.btn_login),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                shape = MaterialTheme.shapes.extraSmall,
                onClick = onLoginClick
            )

            PrimaryButton(
                btnText = stringResource(id = R.string.btn_register),
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                shape = MaterialTheme.shapes.extraSmall
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun StartScreenPreview() {
    StartScreenView(
        onLoginClick = {},
        onRegisterClick = {}
    )
}