package com.samurai.morseencoder.models

import android.content.Context
import com.samurai.sysequsol.R

/**
 * Supported translation languages.
 */
enum class LanguageCode(val value: String) {
    ENGLISH("english"),
    GERMAN( "german"),
    RUSSIAN("russian");

    companion object {
        fun selectedLanguageIndex(code: LanguageCode): Int {
            val index = LanguageCode.values().indexOfFirst { it == code }
            return if (index == -1) {
                0
            } else {
                index
            }
        }
        fun displayValue(code: LanguageCode, context: Context): String {
            return when (code) {
                ENGLISH -> context.resources.getString(R.string.translation_list_english_title)
                GERMAN -> context.resources.getString(R.string.translation_list_german_title)
                RUSSIAN -> context.resources.getString(R.string.translation_list_russian_title)
            }
        }
        fun getByValueIgnoreCaseOrNull(input: String): LanguageCode? {
            return values().firstOrNull { it.value.equals(input, true) }
        }

        fun getTranslationRules(code: LanguageCode, context: Context): Array<String> {
            return when (code) {
                ENGLISH -> context.resources.getStringArray(R.array.translation_english_alphabet)
                GERMAN -> context.resources.getStringArray(R.array.translation_german_alphabet)
                RUSSIAN -> context.resources.getStringArray(R.array.translation_russian_alphabet)
            }
        }
    }
}