package com.samurai.morseencoder.services.storage

/**
 * Local storage keys.
 * Use for SharedPreferences, KeyStore, FileSystem.
 */
enum class LocalStorageKey(val value: String) {

    TRANSLATION_MODE_ENCODE("encode"),
    TRANSLATION_MODE_DECODE("decode")
}