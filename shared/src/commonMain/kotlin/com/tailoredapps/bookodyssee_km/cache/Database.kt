package com.tailoredapps.bookodyssee_km.cache

import com.squareup.sqldelight.ColumnAdapter
import com.tailoredapps.bookodyssee_km.db.LocalBook
import com.tailoredapps.bookodyssee_km.db.User
import comtailoredappsbookodysseekmcache.Book

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val listOfStringsAdapter = object : ColumnAdapter<List<String>, String> {
        override fun decode(databaseValue: String) =
            if (databaseValue.isNotEmpty()) {
                listOf()
            } else {
                databaseValue.split(",")
            }

        override fun encode(value: List<String>) =
            value.joinToString(separator = ",")
    }

    private val database = BookOdysseeDb(
        driver = databaseDriverFactory.createDriver(),
        BookAdapter = Book.Adapter(authorsAdapter = listOfStringsAdapter)
    )

    private val dbQuery = database.bookOdysseeQueries

    internal fun getUser(username: String): User? {
        val queryResult = dbQuery.selectUser(username = username).executeAsOneOrNull()
        return if (queryResult != null) {
            User(
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
        dbQuery.removeBook(
            userId = userId,
            bookId = bookId
        )
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
