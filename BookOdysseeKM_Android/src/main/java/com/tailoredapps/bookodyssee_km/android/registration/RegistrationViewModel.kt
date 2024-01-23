package com.tailoredapps.bookodyssee_km.android.registration

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.EffectController
import at.florianschuster.control.createEffectController
import com.tailoredapps.bookodyssee_km.UserSDK
import com.tailoredapps.bookodyssee_km.android.control.EffectControllerViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

class RegistrationViewModel(
    private val userSDK: UserSDK
) : EffectControllerViewModel<RegistrationViewModel.Action, RegistrationViewModel.State, RegistrationViewModel.Effect>() {
    sealed class Action {
        data object OnRegisterClick : Action()
        data class ChangeUsername(val username: String) : Action()
        data class ChangePassword(val password: String) : Action()
        data class ChangeConfirmPassword(val repeatPassword: String) : Action()
    }

    sealed class Mutation {
        data class SetUsername(val username: String) : Mutation()
        data class SetPassword(val password: String) : Mutation()
        data class SetConfirmPassword(val confirmPassword: String) : Mutation()
        data class ShowErrorMessage(val isError: Boolean) : Mutation()
    }

    data class State(
        val username: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val isError: Boolean = false
    )

    sealed class Effect {
        data object IsSuccess : Effect()
    }

    override val controller: EffectController<Action, State, Effect> =
        viewModelScope.createEffectController<Action, Mutation, State, Effect>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.ChangeUsername -> flow {
                        emit(Mutation.SetUsername(action.username))
                    }

                    is Action.ChangePassword -> flowOf(Mutation.SetPassword(action.password))
                    is Action.ChangeConfirmPassword -> flowOf(Mutation.SetConfirmPassword(action.repeatPassword))
                    is Action.OnRegisterClick -> flow {
                        val passwordMatch = currentState.password == currentState.confirmPassword
                        if (currentState.username.isEmpty() || currentState.password.isEmpty() || !passwordMatch) {
                            emit(Mutation.ShowErrorMessage(isError = true))
                        } else {
                            emit(Mutation.ShowErrorMessage(isError = false))

                            runCatching {
                                userSDK.getUser(username = currentState.username)
                            }.onSuccess { user ->
                                if (user != null) {
                                    Timber.d("User already exists!")
                                } else {
                                    runCatching {
                                        userSDK.createUser(
                                            username = currentState.username,
                                            password = currentState.password
                                        )
                                    }.onSuccess {
                                        Timber.d("User saved to DB")
                                    }.onFailure {
                                        Timber.d("Error when saving user to DB: $it")
                                    }
                                }
                            }.onFailure {
                                Timber.d("Error when fetching user")
                            }
                            emitEffect(Effect.IsSuccess)
                        }
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetUsername -> previousState.copy(username = mutation.username)
                    is Mutation.SetPassword -> previousState.copy(password = mutation.password)
                    is Mutation.SetConfirmPassword -> previousState.copy(confirmPassword = mutation.confirmPassword)
                    is Mutation.ShowErrorMessage -> previousState.copy(isError = mutation.isError)
                }
            }
        )
}
