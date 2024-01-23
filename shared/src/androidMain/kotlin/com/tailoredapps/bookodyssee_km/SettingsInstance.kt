package com.tailoredapps.bookodyssee_km

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsInstance(private val context: Context) {
    actual fun createSettings(): Settings {
        val delegate = context.getSharedPreferences("bookOdyssee", Context.MODE_PRIVATE)
        val settings: Settings = SharedPreferencesSettings(delegate)
        return settings
    }
}
