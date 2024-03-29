package com.tailoredapps.bookodyssee_km.db

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val password: String
)

@Serializable
data class LocalUser(
    val id: Long,
    val username: String,
    val password: String
)
