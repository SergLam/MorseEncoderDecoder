package com.samurai.morseencoder.translation_logic

import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.MorseCharacters

class Decoding {

    var translationCompleted = true

    private val alpha_eng = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
        "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
        "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+", ""
    )
    private val dottie_eng = arrayOf(
        ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
        ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".-*-*.",
        "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
        "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
        "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", " ", ".*-*-*-*-*.",
        "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-", ".*.*-*-*.*.",
        "-*.*-*.*-*.", "-*.*.*-*.  ", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-", ".*-*-*.*-*.",
        ".*-*.*-*.", ""
    )
    private val alpha_germ = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
        "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
        "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+", "ä", "ö", "ü", "ß", ""
    )
    private val dottie_germ = arrayOf(
        ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
        ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".*-*-*.",
        "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
        "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
        "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", " ",
        ".*-*-*-*-*.", "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-",
        ".*.*-*-*.*.", "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-",
        ".*-*-*.*-*.", ".*-*.*-*.", ".*-*.*-", "-*-*-*.", ".*.*-*-", ".*.*.*-*-*.*.", ""
    )
    private val alpha_rus = arrayOf(
        "а", "б", "в", "г", "д", "е", "ж", "з",
        "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
        "у", "ф", "х", "ц", "ш", "щ", "э", "ю", "я", "ь", "ы",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", "'",
        ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+", ""
    )
    private val dottie_rus = arrayOf(
        ".*-",
        "-*.*.*.",
        ".*-*-",
        "-*-*.",
        "-*.*.",
        ".",
        ".*.*.*-",
        "-*-*.*.",
        ".*.",
        ".*-*-*-",
        "-*.*-*.",
        ".*-*.*.",
        "-*-",
        "-*.",
        "-*-*-",
        ".*-*-*.",
        ".*-*.",
        ".*.*.",
        "-",
        ".*.*-",
        ".*.*-*.",
        ".*.*.*.",
        "-*.*-*.",
        "-*-*-*-",
        "-*-*.*-",
        ".*.*-*.*.",
        ".*.*-*-",
        ".*-*.*-",
        "-*.*.*-",
        "-*.*-",
        ".*-*-*-*-",
        ".*.*-*-*-",
        ".*.*.*-*-",
        ".*.*.*.*-",
        ".*.*.*.*.",
        "-*.*.*.*.",
        "-*-*.*.*.",
        "-*-*-*.*.",
        "-*-*-*-*.",
        "-*-*-*-*-",
        " ",
        ".*-*-*-*-*.",
        "-*-*-*.*.*.",
        "-*-*.*.*-*-",
        "-*.*.*.*.*-",
        "-*.*-*-*.*-",
        ".*-*.*-*.*-",
        ".*.*-*-*.*.",
        "-*.*-*.*-*.",
        "-*.*.*-*.",
        ".*.*-*-*.*-",
        "-*-*-*.*.",
        "-*.*.*.*-",
        ".*-*-*.*-*.",
        ".*-*.*-*.",
        ""
    )

    //. . . -   . . .   - - .       . - - -   . . . -   . - . - .
    //.*.*.*-***.*.*.***-*-*.*******.*-*-*-***.*.*.*-***.*-*.*-*.
    fun code_to_text(code: String, lang: String): String {
        // Get language selected in settings
        var result = ""
        translationCompleted = true
        if (lang == LanguageCode.RUSSIAN.value) {
            result = morse_to_rus(code)
        }
        if (lang == LanguageCode.GERMAN.value) {
            result = morse_to_germ(code)
        }
        if (lang == LanguageCode.ENGLISH.value) {
            result = morse_to_eng(code)
        }
        return result
    }

    private fun morse_to_eng(code: String): String {
        var code = code
        var result = ""
        var is_match = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val code_split = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in code_split.indices) {
            is_match = false
            for (i in alpha_eng.indices) {
                if (dottie_eng[i] == code_split[j]) {
                    is_match = true
                    result += alpha_eng[i]
                }
            }
            if (is_match == true) {
            } else translationCompleted = false
        }
        return result
    }

    private fun morse_to_germ(code: String): String {
        var code = code
        var result = ""
        var is_match = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val code_split = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in code_split.indices) {
            is_match = false
            for (i in alpha_germ.indices) {
                if (dottie_germ[i] == code_split[j]) {
                    is_match = true
                    result += alpha_germ[i]
                }
            }
            if (is_match == true) {
            } else translationCompleted = false
        }
        return result
    }

    private fun morse_to_rus(code: String): String {
        var code = code
        var result = ""
        var is_match = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val code_split = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in code_split.indices) {
            is_match = false
            for (i in alpha_rus.indices) {
                if (dottie_rus[i] == code_split[j]) {
                    is_match = true
                    result += alpha_rus[i]
                }
            }
            if (is_match) {

            } else translationCompleted = false
        }
        return result
    }
}