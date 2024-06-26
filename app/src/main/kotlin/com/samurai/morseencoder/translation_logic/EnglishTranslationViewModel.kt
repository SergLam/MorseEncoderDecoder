package com.samurai.morseencoder.translation_logic

import androidx.lifecycle.ViewModel
import com.samurai.morseencoder.models.MorseCharacters
import java.util.regex.Pattern

class EnglishTranslationViewModel : ViewModel() {

    private val englishAlphabet = arrayOf(
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
        ""
    )
    private val englishMorseCodes = arrayOf(
        ".*-", "-*.*.*.", "-*.*-*.",
        "-*.*.", ".", ".*.*-*.",
        "-*-*.", ".*.*.*.", ".*.",
        ".*-*-*-", "-*.*-", ".*-*.*.",
        "-*-", "-*.", "-*-*-",
        ".-*-*.", "-*-*.*-", ".*-*.",
        ".*.*.", "-", ".*.*-",
        ".*.*.*-", ".*-*-", "-*.*.*-",
        "-*.*-*-", "-*-*.*.", ".*-*-*-*-",
        ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-",
        ".*.*.*.*.", "-*.*.*.*.", "-*-*.*.*.",
        "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-",
        " ", ".*-*-*-*-*.", "-*-*-*.*.*.",
        "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-",
        ".*-*.*-*.*-", ".*.*-*-*.*.", "-*.*-*.*-*.",
        "-*.*.*-*.  ", ".*.*-*-*.*-", "-*-*-*.*.",
        "-*.*.*.*-", ".*-*-*.*-*.", ".*-*.*-*.", ""
    )

    fun morseToEnglish(code: String): String {
        var code = code
        var result = ""
        var isMatch = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val codeSplit = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in codeSplit.indices) {
            isMatch = false
            for (i in englishAlphabet.indices) {
                if (englishMorseCodes[i] == codeSplit[j]) {
                    isMatch = true
                    result += englishMorseCodes[i]
                }
            }
            if (!isMatch) {
                // TODO: - Throw error here
            }
        }
        return result
    }

    fun englishToMorse(translates: CharArray): String {
        var morse = ""
        for (j in translates.indices) {
            val a = translates[j]
            val b = a.toString()
            for (i in englishAlphabet.indices) {
                if (j < translates.size - 1) {
                    if (englishAlphabet[i] == b && translates[j + 1].toString() != MorseCharacters.SPACE.value) {
                        morse += englishMorseCodes[i] + MorseCharacters.LETTER_SPACING.value
                    }
                    if (englishAlphabet[i] == b && translates[j + 1].toString() == MorseCharacters.SPACE.value) {
                        morse += englishMorseCodes[i]
                    }
                } else if (englishAlphabet[i] == b) {
                    morse += englishMorseCodes[i]
                }
            }
        }
        return morse
    }

    fun isEnglish(text: String): Boolean {
        val pattern = Pattern.compile(
            "[" +  //начало списка допустимых символов
                    "a-zA-Z" +
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