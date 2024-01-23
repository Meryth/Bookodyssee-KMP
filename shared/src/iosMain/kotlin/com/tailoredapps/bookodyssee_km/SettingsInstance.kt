package com.tailoredapps.bookodyssee_km

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual class SettingsInstance() {
    actual fun createSettings(): Settings {
        val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults
        val settings: Settings = NSUserDefaultsSettings(delegate)
        return settings
    }
}