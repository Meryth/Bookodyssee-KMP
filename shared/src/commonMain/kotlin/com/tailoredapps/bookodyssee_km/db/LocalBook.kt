package com.tailoredapps.bookodyssee_km.db

import kotlinx.serialization.Serializable

enum class ReadingState {
    NOT_ADDED, TO_READ, CURRENTLY_READING, FINISHED
}

@Serializable
data class LocalBook(
    val userId: Int,
    val bookId: String,
    val title: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String,
    val pageCount: Int? = null,
    val imageLink: String? = null,
    val readingState: ReadingState = ReadingState.TO_READ
)
