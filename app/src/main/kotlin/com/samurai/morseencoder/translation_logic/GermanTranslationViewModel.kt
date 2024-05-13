package com.samurai.morseencoder.translation_logic

import androidx.lifecycle.ViewModel
import com.samurai.morseencoder.models.MorseCharacters
import java.util.regex.Pattern

class GermanTranslationViewModel : ViewModel() {

    private val alphabetGerman = arrayOf(
        "a", "b", "c",
        "d", "e", "f",
        "g", "h", "i",
        "j", "k", "l",
        "m", "n", "o",
        "p", "q", "r",
        "s", "t", "u",
        "v", "w", "x",
        "y", "z", "1",
        "2", "3", "4",
        "5", "6", "7",
        "8", "9", "0",
        " ", "'", ":",
        ",", "-", "(",
        ".", "?", ";",
        "/", "-", ")",
        "=", "@", "+",
        "ä", "ö", "ü",
        "ß"
    )
    private val germanMorseCodes = arrayOf(
        ".*-", "-*.*.*.", "-*.*-*.",
        "-*.*.", ".", ".*.*-*.",
        "-*-*.", ".*.*.*.", ".*.",
        ".*-*-*-", "-*.*-", ".*-*.*.",
        "-*-", "-*.", "-*-*-",
        ".*-*-*.", "-*-*.*-", ".*-*.",
        ".*.*.", "-", ".*.*-",
        ".*.*.*-", ".*-*-", "-*.*.*-",
        "-*.*-*-", "-*-*.*.", ".*-*-*-*-",
        ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-",
        ".*.*.*.*.", "-*.*.*.*.", "-*-*.*.*.",
        "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-",
        "*******", ".*-*-*-*-*.", "-*-*-*.*.*.",
        "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-",
        ".*-*.*-*.*-", ".*.*-*-*.*.", "-*.*-*.*-*.",
        "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.",
        "-*.*.*.*-", ".*-*-*.*-*.", ".*-*.*-*.",
        ".*-*.*-", "-*-*-*.", ".*.*-*-",
        ".*.*.*-*-*.*."
    )

    fun morseToGerman(code: String): String {
        var code = code
        var result = ""
        var isMatch = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val codeSplit = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in codeSplit.indices) {
            isMatch = false
            for (i in alphabetGerman.indices) {
                if (germanMorseCodes[i] == codeSplit[j]) {
                    isMatch = true
                    result += alphabetGerman[i]
                }
            }
            if (!isMatch) {
                // TODO: - Throw error here
            }
        }
        return result
    }

    fun germanToMorse(translates: CharArray): String {
        var morse = ""
        for (characterIndex in translates.indices) {
            val a = translates[characterIndex]
            val b = a.toString()
            for (alphabetIndex in alphabetGerman.indices) {
                if (characterIndex < translates.size - 1) {
                    if (alphabetGerman[alphabetIndex] == b && translates[characterIndex + 1].toString() != MorseCharacters.SPACE.value) {
                        morse += germanMorseCodes[alphabetIndex] + MorseCharacters.LETTER_SPACING.value
                    }
                    if (alphabetGerman[alphabetIndex] == b && translates[characterIndex + 1].toString() == MorseCharacters.SPACE.value) {
                        morse += germanMorseCodes[alphabetIndex]
                    }
                } else if (alphabetGerman[alphabetIndex] == b) {
                    morse += germanMorseCodes[alphabetIndex]
                }
            }
        }
        return morse
    }

    fun isGerman(text: String): Boolean {
        val pattern = Pattern.compile(
            "[" +  //начало списка допустимых символов
                    "a-zA-ZäÄöÖüÜß" +
                    "\\d" +  //цифры
                    "\\s" +  //знаки-разделители (пробел, табуляция и т.д.)
                    "\\p{Punct}" +  //знаки пунктуации
                    "]" +  //конец списка допустимых символов
                    "*"
        ) //допускается наличие указанных символов в любом количестве
        val matcher = pattern.matcher(text)
        return matcher.matches()
    }
}