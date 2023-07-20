package com.samurai.morseencoder.models

/**
 * Morse-specific characters for correct translation.
 */
enum class MorseCharacters(val value: String) {
    COMMA(","),
    SPACE(" "),
    LETTER_SPACING("***"),
    WORDS_SPACING("*******"),
    COMMAS_WITH_SPACE(", ,")
}
