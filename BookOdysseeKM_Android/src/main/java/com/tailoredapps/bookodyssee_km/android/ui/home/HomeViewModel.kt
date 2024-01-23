package com.tailoredapps.bookodyssee_km.android.ui.home

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

class HomeViewModel(
    private val bookOdysseeSDK: BookOdysseeSDK,
    private val settings: Settings
) : ControllerViewModel<HomeViewModel.Action, HomeViewModel.State>() {

    sealed class Action {
        data object GetSavedBooks : Action()
    }

    sealed class Mutation {
        data class SetReadingList(
            val toReadList: List<LocalBook>,
            val currentlyReadingList: List<LocalBook>
        ) : Mutation()

        data class SetLoadingState(val isLoading: Boolean) : Mutation()
    }

    data class State(
        val toReadList: List<LocalBook> = emptyList(),
        val currentlyReadingList: List<LocalBook> = emptyList(),
        val isLoading: Boolean = false
    )

    override val controller: Controller<Action, State> =
        viewModelScope.createController<Action, Mutation, State>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.GetSavedBooks -> flow {
                        emit(Mutation.SetLoadingState(true))
                        runCatching {
                            bookOdysseeSDK.getAllBooksByUser(settings.getLong("userId", 0))
                        }.onSuccess { bookList ->
                            val toReadList =
                                bookList.filter { it.readingState == ReadingState.TO_READ.state }
                            val currentlyReadingList =
                                bookList.filter { it.readingState == ReadingState.CURRENTLY_READING.state }

                            emit(
                                Mutation.SetReadingList(
                                    toReadList = toReadList,
                                    currentlyReadingList = currentlyReadingList
                                )
                            )
                        }.onFailure {
                            Timber.e("Error: Could not get saved books from user! $it")
                        }

                        emit(Mutation.SetLoadingState(false))
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetLoadingState -> previousState.copy(isLoading = mutation.isLoading)
                    is Mutation.SetReadingList -> previousState.copy(
                        toReadList = mutation.toReadList,
                        currentlyReadingList = mutation.currentlyReadingList
                    )
                }
            }
        )
}