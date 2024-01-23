package com.tailoredapps.bookodyssee_km.cache

import com.tailoredapps.bookodyssee_km.db.LocalBook
import com.tailoredapps.bookodyssee_km.db.LocalUser
import com.tailoredapps.bookodyssee_km.db.User

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = BookOdysseeDb(
        driver = databaseDriverFactory.createDriver()
    )

    private val dbQuery = database.bookOdysseeQueries

    internal fun getUser(username: String): LocalUser? {
        val queryResult = dbQuery.selectUser(username = username).executeAsOneOrNull()
        return if (queryResult != null) {
            LocalUser(
                id = queryResult.id,
                username = queryResult.username.orEmpty(),
                password = queryResult.password.orEmpty()
            )
        } else {
            null
        }
    }

    internal fun createUser(user: User) {
        dbQuery.transaction {
            insertUser(user)
        }
    }

    internal fun getAllBooksByUser(userId: Long): List<LocalBook> {
        val queryResult = dbQuery.selectAllBooksByUser(userId).executeAsList()
        return if (queryResult.isNotEmpty()) {
            queryResult.map { book ->
                LocalBook(
                    userId = book.userId ?: 0,
                    bookId = book.bookId.orEmpty(),
                    title = book.title,
                    authors = book.authors,
                    publisher = book.publisher,
                    publishedDate = book.publishedDate.orEmpty(),
                    pageCount = book.pageCount,
                    imageLink = book.imageLink,
                    readingState = book.readingState.orEmpty()
                )
            }
        } else {
            emptyList()
        }
    }

    internal fun getBook(userId: Long, bookId: String): LocalBook? {
        val queryResult = dbQuery.selectBook(
            userId = userId,
            bookId = bookId
        ).executeAsOneOrNull()

        return if (queryResult != null) {
            LocalBook(
                userId = queryResult.userId ?: 0,
                bookId = queryResult.bookId.orEmpty(),
                title = queryResult.title,
                authors = queryResult.authors,
                publisher = queryResult.publisher,
                publishedDate = queryResult.publishedDate.orEmpty(),
                pageCount = queryResult.pageCount,
                imageLink = queryResult.imageLink,
                readingState = queryResult.readingState.orEmpty(),
            )
        } else {
            null
        }
    }

    internal fun addBookToReadingList(book: LocalBook) {
        dbQuery.transaction {
            insertBook(book)
        }
    }

    internal fun updateReadingState(readingState: String, userId: Long, bookId: String) {
        dbQuery.transaction {
            updateBook(
                readingState = readingState,
                userId = userId,
                bookId = bookId
            )
        }
    }

    internal fun removeBookFromReadingList(userId: Long, bookId: String) {
        dbQuery.transaction {
            removeBook(
                userId = userId,
                bookId = bookId
            )
        }
    }

    private fun insertUser(user: User) {
        dbQuery.insertUser(
            username = user.username,
            password = user.password
        )
    }

    private fun insertBook(book: LocalBook) {
        dbQuery.insertBook(
            userId = book.userId,
            bookId = book.bookId,
            title = book.title,
            authors = book.authors,
            publisher = book.publisher,
            publishedDate = book.publishedDate,
            pageCount = book.pageCount,
            imageLink = book.imageLink,
            readingState = book.readingState,
        )
    }

    private fun updateBook(readingState: String, userId: Long, bookId: String) {
        dbQuery.updateBook(
            readingState = readingState,
            bookId = bookId,
            userId = userId
        )
    }

    private fun removeBook(userId: Long, bookId: String) {
        dbQuery.removeBook(
            userId = userId,
            bookId = bookId
        )
    }
}
