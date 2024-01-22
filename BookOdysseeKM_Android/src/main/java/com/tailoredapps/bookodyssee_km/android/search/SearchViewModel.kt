package com.tailoredapps.bookodyssee_km.android.search

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.tailoredapps.bookodyssee_km.BookItem
import com.tailoredapps.bookodyssee_km.DataRepo
import com.tailoredapps.bookodyssee_km.android.control.ControllerViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

class SearchViewModel(

) : ControllerViewModel<SearchViewModel.Action, SearchViewModel.State>() {
    sealed class Action {
        data class OnQueryChange(val query: String) : Action()
        data class OnSearchClick(val query: String) : Action()
    }

    sealed class Mutation {
        data class SetQuery(val query: String) : Mutation()
        data class SetResultList(val searchResult: List<BookItem>) : Mutation()
    }

    data class State(
        val query: String = "",
        val searchResult: List<BookItem> = emptyList()
    )

    override val controller: Controller<Action, State> =
        viewModelScope.createController<Action, Mutation, State>(
            initialState = State(),
            mutator = { action ->
                when (action) {
                    is Action.OnQueryChange -> flowOf(Mutation.SetQuery(action.query))
                    is Action.OnSearchClick -> flow {
                        DataRepo().searchBooks().catch {
                            Timber.e("Error while searching for books: $it")
                        }.collect { remoteBookList ->
                            emit(Mutation.SetResultList(remoteBookList.items))
                        }
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetQuery -> previousState.copy(query = mutation.query)
                    is Mutation.SetResultList -> previousState.copy(searchResult = mutation.searchResult)
                }
            }
        )
}