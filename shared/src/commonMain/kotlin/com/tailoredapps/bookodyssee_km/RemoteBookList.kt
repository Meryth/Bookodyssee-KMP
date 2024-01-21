package com.tailoredapps.bookodyssee_km

import kotlinx.serialization.Serializable

@Serializable
data class RemoteBookList(
    val items: List<BookItem>,
)

@Serializable
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val imageLinks: BookImageLinks?
)

@Serializable
data class BookImageLinks(
    val thumbnail: String?
)
