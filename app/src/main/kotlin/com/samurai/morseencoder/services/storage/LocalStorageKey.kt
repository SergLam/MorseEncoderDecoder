package com.samurai.morseencoder.services.storage

/**
 * Local storage keys.
 * Use for SharedPreferences, KeyStore, FileSystem.
 */
enum class LocalStorageKey(val value: String) {

    TRANSLATION_MODE("mode"),
    TRANSLATION_LANGUAGE("language");

    companion object {
        // Default preferences file name.
        const val APP_PREFERENCES = "app_settings"
    }
}