package com.tailoredapps.bookodyssee_km.db

import kotlinx.serialization.Serializable

enum class ReadingState(val state: String) {
    NOT_ADDED("NOT_ADDED"),
    TO_READ("TO_READ"),
    CURRENTLY_READING("READING"),
    FINISHED("FINISHED")
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
    val readingState: String = ReadingState.TO_READ.state
)
