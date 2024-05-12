package com.samurai.morseencoder.translation_logic

import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.MorseCharacters

class Decoding {

    var translationCompleted = true

    private val alphaEng = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
        "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
        "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+", ""
    )
    private val dottieEng = arrayOf(
        ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
        ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".-*-*.",
        "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
        "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
        "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", " ", ".*-*-*-*-*.",
        "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-", ".*.*-*-*.*.",
        "-*.*-*.*-*.", "-*.*.*-*.  ", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-", ".*-*-*.*-*.",
        ".*-*.*-*.", ""
    )
    private val alphaGerm = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
        "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
        "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+", "ä", "ö", "ü", "ß", ""
    )
    private val dottieGerm = arrayOf(
        ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
        ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".*-*-*.",
        "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
        "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
        "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", " ",
        ".*-*-*-*-*.", "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-",
        ".*.*-*-*.*.", "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-",
        ".*-*-*.*-*.", ".*-*.*-*.", ".*-*.*-", "-*-*-*.", ".*.*-*-", ".*.*.*-*-*.*.", ""
    )
    private val alphaRus = arrayOf(
        "а", "б", "в", "г", "д", "е", "ж", "з",
        "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
        "у", "ф", "х", "ц", "ш", "щ", "э", "ю", "я", "ь", "ы",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", "'",
        ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+", ""
    )
    private val dottieRus = arrayOf(
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
    fun codeToText(code: String, lang: LanguageCode): String {
        // Get language selected in settings
        val result = when (lang) {
            LanguageCode.RUSSIAN -> morseToRus(code)
            LanguageCode.GERMAN -> morseToGerm(code)
            LanguageCode.ENGLISH -> morseToEng(code)
        }
        translationCompleted = true
        return result
    }

    private fun morseToEng(code: String): String {
        var code = code
        var result = ""
        var isMatch = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val code_split = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in code_split.indices) {
            isMatch = false
            for (i in alphaEng.indices) {
                if (dottieEng[i] == code_split[j]) {
                    isMatch = true
                    result += alphaEng[i]
                }
            }
            if (!isMatch) {
                translationCompleted = false
            }
        }
        return result
    }

    private fun morseToGerm(code: String): String {
        var code = code
        var result = ""
        var isMatch = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val codeSplit = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in codeSplit.indices) {
            isMatch = false
            for (i in alphaGerm.indices) {
                if (dottieGerm[i] == codeSplit[j]) {
                    isMatch = true
                    result += alphaGerm[i]
                }
            }
            if (!isMatch) {
                translationCompleted = false
            }
        }
        return result
    }

    private fun morseToRus(code: String): String {
        var code = code
        var result = ""
        var isMatch = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val codeSplit = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in codeSplit.indices) {
            isMatch = false
            for (i in alphaRus.indices) {
                if (dottieRus[i] == codeSplit[j]) {
                    isMatch = true
                    result += alphaRus[i]
                }
            }
            if (!isMatch) {
                translationCompleted = false
            }
        }
        return result
    }
}