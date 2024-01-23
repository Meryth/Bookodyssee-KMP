package com.tailoredapps.bookodyssee_km.android.login

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.EffectController
import at.florianschuster.control.createEffectController
import com.tailoredapps.bookodyssee_km.UserSDK
import com.tailoredapps.bookodyssee_km.android.control.EffectControllerViewModel
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class LoginViewModel(
    private val userSDK: UserSDK
//    private val sharedPrefs: SharedPrefs
) : EffectControllerViewModel<LoginViewModel.Action, LoginViewModel.State, LoginViewModel.Effect>() {
    sealed class Action {
        data object OnLoginClick : Action()
        data class ChangeUsername(val username: String) : Action()
        data class ChangePassword(val password: String) : Action()
    }

    sealed class Mutation {
        data class SetUsername(val username: String) : Mutation()
        data class SetPassword(val password: String) : Mutation()
        data class ShowErrorMessage(val isError: Boolean) : Mutation()
    }

    data class State(
        val username: String = "",
        val password: String = "",
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

                    is Action.ChangePassword -> flow {
                        emit(Mutation.SetPassword(action.password))
                    }

                    is Action.OnLoginClick -> flow {
                        emit(Mutation.ShowErrorMessage(false))

                        runCatching {
                            userSDK.getUser(currentState.username)
                        }.onSuccess { user ->
                            Timber.d("aaa user is $user")

                            if (user != null && user.password == currentState.password) {
                                Timber.d("omg omg omg omg")
                            }
                        }.onFailure {
                            Timber.d("why you like this $it")
                        }

                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetUsername -> previousState.copy(username = mutation.username)
                    is Mutation.SetPassword -> previousState.copy(password = mutation.password)
                    is Mutation.ShowErrorMessage -> previousState.copy(isError = mutation.isError)
                }
            }
        )
}