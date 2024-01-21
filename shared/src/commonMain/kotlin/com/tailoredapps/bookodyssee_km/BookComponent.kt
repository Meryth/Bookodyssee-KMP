package com.tailoredapps.bookodyssee_km

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val TEMP_API_KEY = "AIzaSyCB0pJ6U7O32HS2J4WogSM31LsIvVleJws"
const val BASE_URL = "https://www.googleapis.com"

class BookComponent {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun findBookBySearchTerm(searchTerm: String): RemoteBookList {
        return httpClient.get("$BASE_URL/books/v1/volumes?") {
            url {
                header("X-goog-api-key", TEMP_API_KEY)
                parameters.append("q", searchTerm)
            }
        }.body<RemoteBookList>()
    }
}