package com.tailoredapps.bookodyssee_km

import com.tailoredapps.bookodyssee_km.cache.Database
import com.tailoredapps.bookodyssee_km.cache.DatabaseDriverFactory
import com.tailoredapps.bookodyssee_km.db.LocalBook
import com.tailoredapps.bookodyssee_km.db.User


class BookOdysseeSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    fun getUser(username: String): User? {
        return database.getUser(username)
    }

    fun createUser(username: String, password: String) {
        database.createUser(
            User(
                username = username,
                password = password
            )
        )
    }

    fun getBook(userId: Long, bookId: String): LocalBook? {
        return database.getBook(
            userId = userId,
            bookId = bookId
        )
    }

    fun addBookToReadingList(book: LocalBook) {
        database.addBookToReadingList(book)
    }

    fun updateReadingState(readingState: String, userId: Long, bookId: String) {
        database.updateReadingState(
            readingState = readingState,
            userId = userId,
            bookId = bookId
        )
    }

    fun removeBookFromReadingList(userId: Long, bookId: String) {
        database.removeBookFromReadingList(
            userId = userId,
            bookId = bookId
        )
    }
}
