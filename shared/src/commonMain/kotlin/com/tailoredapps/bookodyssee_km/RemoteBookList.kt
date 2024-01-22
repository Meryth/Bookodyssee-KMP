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
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String,
    val description: String? = null,
    val pageCount: Int? = null,
    val imageLinks: BookImageLinks? = null
)

@Serializable
data class BookImageLinks(
    val thumbnail: String? = null
)
