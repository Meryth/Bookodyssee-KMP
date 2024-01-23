package com.tailoredapps.bookodyssee_km.db

import kotlinx.serialization.Serializable

enum class ReadingState(val state: String) {
    NOT_ADDED("NOT_ADDED"),
    TO_READ("TO_READ"),
    CURRENTLY_READING("READING"),
    FINISHED("FINISHED")
}

fun convertListToString(value: List<String>) =
    value.joinToString(separator = ";")

fun convertStringToList(databaseValue: String) =
    databaseValue.split(";")

@Serializable
data class LocalBook(
    val userId: Long,
    val bookId: String,
    val title: String? = null,
    val authors: String? = null,
    val publisher: String? = null,
    val publishedDate: String,
    val pageCount: Long? = null,
    val imageLink: String? = null,
    val readingState: String = ReadingState.TO_READ.state
)
