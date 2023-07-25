package com.samurai.morseencoder.models

/**
 * Translation mode, that currently active in the app.
 */
enum class TranslationMode(val value: String) {

    ENCODE("encode"),
    DECODE("decode");

    companion object {
        fun getByValue(value: String): TranslationMode? {
            return TranslationMode.values().firstOrNull { it.value.equals(value, true) }
        }
    }
}