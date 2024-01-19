package com.tailoredapps.bookodyssee_km.android.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tailoredapps.bookodyssee_km.android.R
import com.tailoredapps.bookodyssee_km.android.base_ui.DefaultTextField
import com.tailoredapps.bookodyssee_km.android.base_ui.PrimaryButton
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = getViewModel()
) {
    val state by viewModel.state.collectAsState()

    LoginView(
        username = state.username,
        password = state.password,
        isError = state.isError,
        onUsernameChange = { viewModel.dispatch(LoginViewModel.Action.ChangeUsername(it)) },
        onPasswordChange = { viewModel.dispatch(LoginViewModel.Action.ChangePassword(it)) },
        onLoginClick = { viewModel.dispatch(LoginViewModel.Action.OnLoginClick) }
    )

    LaunchedEffect(Unit) {
        viewModel.effects.onEach { effect ->
            when (effect) {
                is LoginViewModel.Effect.IsSuccess -> onLoginSuccess()
            }
        }.launchIn(this)
    }
}

@Composable
fun LoginView(
    username: String,
    password: String,
    isError: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Scaffold(
//        title = stringResource(id = R.string.app_name)
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding())
                .padding(horizontal = 24.dp)
        ) {
            DefaultTextField(
                value = username,
                label = stringResource(R.string.lb_username),
                singleLine = true,
                onValueChange = { onUsernameChange(it) }
            )
            Column(
                modifier = Modifier.padding(vertical = 48.dp)
            ) {
                DefaultTextField(
                    value = password,
                    label = stringResource(R.string.lb_password),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { onPasswordChange(it) }
                )

                if (isError) {
                    Text(
                        text = stringResource(R.string.error_login),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }

            PrimaryButton(
                btnText = stringResource(id = R.string.btn_login),
                onClick = onLoginClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    LoginView(
        username = "bob",
        password = "hilfmeier",
        isError = false,
        onUsernameChange = {},
        onPasswordChange = {},
        onLoginClick = {}
    )
}