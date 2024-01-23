package com.tailoredapps.bookodyssee_km.android.book

import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.tailoredapps.bookodyssee_km.BookItem
import com.tailoredapps.bookodyssee_km.BookOdysseeSDK
import com.tailoredapps.bookodyssee_km.DataRepo
import com.tailoredapps.bookodyssee_km.android.control.ControllerViewModel
import com.tailoredapps.bookodyssee_km.db.LocalBook
import com.tailoredapps.bookodyssee_km.db.ReadingState
import com.tailoredapps.bookodyssee_km.db.convertListToString
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import timber.log.Timber

class BookViewModel(
    private val bookOdysseeSDK: BookOdysseeSDK,
    bookId: String
) : ControllerViewModel<BookViewModel.Action, BookViewModel.State>() {
    sealed class Action {
        data object LoadBookData : Action()
        data object AddBookToReadingList : Action()
        data object RemoveBookFromReadingList : Action()
        data class ChangeReadingState(val readingState: String) : Action()
    }

    sealed class Mutation {
        data class SetBookData(val bookItem: BookItem) : Mutation()
        data class SetReadingState(val readingState: String) : Mutation()
    }

    data class State(
        val bookItem: BookItem? = null,
        val readingState: String = ReadingState.NOT_ADDED.state
    )

    override val controller: Controller<Action, State> =
        viewModelScope.createController<Action, Mutation, State>(
            initialState = State(),
            actionsTransformer = { actions ->
                merge(
                    actions,
                    flowOf(Action.LoadBookData)
                )
            },
            mutator = { action ->
                when (action) {
                    is Action.LoadBookData -> flow {
                        DataRepo().getBookById(bookId).catch {
                            Timber.e("Error while fetching book: $it")
                        }.collect { bookItem ->
                            emit(Mutation.SetBookData(bookItem = bookItem))
                        }

                        runCatching {
                            bookOdysseeSDK.getBook(userId = 0, bookId = bookId)
                        }.onSuccess { book ->
                            Timber.d("aaa book is $book")
                            if (book != null) {
                                emit(Mutation.SetReadingState(book.readingState))
                            }
                        }.onFailure {
                            Timber.e("Error when checking book $it")
                        }
                    }

                    is Action.AddBookToReadingList -> flow {
                        runCatching {
                            val book = currentState.bookItem?.volumeInfo
                            if (book != null) {
                                bookOdysseeSDK.addBookToReadingList(
                                    book = LocalBook(
                                        userId = 0,
                                        bookId = bookId,
                                        authors = convertListToString(book.authors.orEmpty()),
                                        title = book.title,
                                        publisher = book.publisher,
                                        publishedDate = book.publishedDate,
                                        pageCount = book.pageCount?.toLong(),
                                        imageLink = book.imageLinks?.thumbnail.orEmpty(),
                                        readingState = ReadingState.TO_READ.state
                                    )
                                )
                            }
                        }.onSuccess {
                            emit(Mutation.SetReadingState(ReadingState.TO_READ.state))
                        }.onFailure {
                            Timber.e("Error: Book could not be added $it")
                        }
                    }

                    is Action.ChangeReadingState -> flow {
                        runCatching {
                            bookOdysseeSDK.updateReadingState(
                                readingState = action.readingState,
                                userId = 0,
                                bookId = bookId
                            )
                        }.onSuccess {
                            emit(Mutation.SetReadingState(action.readingState))
                        }.onFailure {
                            Timber.e("Error: Reading state of book could not be updated $it")
                        }
                    }

                    is Action.RemoveBookFromReadingList -> flow {
                        runCatching {
                            bookOdysseeSDK.removeBookFromReadingList(
                                userId = 0,
                                bookId = bookId
                            )
                        }.onSuccess {
                            emit(Mutation.SetReadingState(ReadingState.NOT_ADDED.state))
                        }.onFailure {
                            Timber.e("Error: Book could not be removed $it")
                        }
                    }
                }
            },
            reducer = { mutation, previousState ->
                when (mutation) {
                    is Mutation.SetBookData -> previousState.copy(bookItem = mutation.bookItem)
                    is Mutation.SetReadingState -> previousState.copy(readingState = mutation.readingState)
                }
            }
        )
}
