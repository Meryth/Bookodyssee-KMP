package com.tailoredapps.bookodyssee_km.android.ui.finished

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.russhwolf.settings.Settings
import com.tailoredapps.bookodyssee_km.BookOdysseeSDK
import com.tailoredapps.bookodyssee_km.android.control.ControllerViewModel
import com.tailoredapps.bookodyssee_km.db.LocalBook
import com.tailoredapps.bookodyssee_km.db.ReadingState
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class FinishedViewModel(
    private val bookOdysseeSDK: BookOdysseeSDK,
    private val settings: Settings
) : ControllerViewModel<FinishedViewModel.Action, FinishedViewModel.State>() {
    sealed class Action {
        data object GetFinishedBooks : Action()
    }

    sealed class Mutation {
        data class SetFinishedBookList(val bookList: List<LocalBook>) : Mutation()
        data class SetLoadingState(val isLoading: Boolean) : Mutation()
    }

    data class State(
        val finishedBookList: List<LocalBook> = emptyList(),
        val isLoading: Boolean = false
    )

    override val controller: Controller<Action, State> =
        viewModelScope.createController<Action, Mutation, State>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.GetFinishedBooks -> flow {
                        emit(Mutation.SetLoadingState(true))

                        runCatching {
                            bookOdysseeSDK.getAllBooksByUser(settings.getLong("userId", 0))
                        }.onSuccess { bookList ->
                            val finishedBooks =
                                bookList.filter { it.readingState == ReadingState.FINISHED.state }
                            emit(Mutation.SetFinishedBookList(finishedBooks))
                        }.onFailure {
                            Timber.e("Could not retrieve book list of user! $it")
                        }
                        emit(Mutation.SetLoadingState(false))
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetLoadingState -> previousState.copy(isLoading = mutation.isLoading)
                    is Mutation.SetFinishedBookList -> previousState.copy(finishedBookList = mutation.bookList)
                }
            }
        )
}