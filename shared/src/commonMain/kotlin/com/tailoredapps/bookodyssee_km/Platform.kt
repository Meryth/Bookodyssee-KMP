package com.tailoredapps.bookodyssee_km

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform