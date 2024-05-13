package com.samurai.morseencoder.translation_logic

import androidx.lifecycle.ViewModel
import com.samurai.morseencoder.models.MorseCharacters
import java.util.regex.Pattern

class RussianTranslationViewModel: ViewModel() {

    private val alphabetRussian = arrayOf(
        "а", "б", "в",
        "г", "д", "е",
        "ж", "з", "и",
        "й", "к", "л",
        "м", "н", "о",
        "п", "р", "с",
        "т", "у", "ф",
        "х", "ц", "ш",
        "щ", "э", "ю",
        "я", "ь", "ы",
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9",
        "0", " ", "'",
        ":", ",", "-",
        "(", ".", "?",
        ";", "/", "-",
        ")", "=", "@",
        "+", ""
    )
    private val russianMorseCodes = arrayOf(
        ".*-", "-*.*.*.", ".*-*-",
        "-*-*.", "-*.*.", ".",
        ".*.*.*-", "-*-*.*.", ".*.",
        ".*-*-*-", "-*.*-*.", ".*-*.*.",
        "-*-", "-*.", "-*-*-",
        ".*-*-*.", ".*-*.", ".*.*.",
        "-", ".*.*-", ".*.*-*.",
        ".*.*.*.", "-*.*-*.", "-*-*-*-",
        "-*-*.*-", ".*.*-*.*.", ".*.*-*-",
        ".*-*.*-", "-*.*.*-", "-*.*-",
        ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-",
        ".*.*.*.*-", ".*.*.*.*.", "-*.*.*.*.",
        "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.",
        "-*-*-*-*-", " ", ".*-*-*-*-*.",
        "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-",
        "-*.*-*-*.*-", ".*-*.*-*.*-", ".*.*-*-*.*.",
        "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-",
        "-*-*-*.*.", "-*.*.*.*-", ".*-*-*.*-*.",
        ".*-*.*-*.", ""
    )

    fun morseToRussian(code: String): String {
        var code = code
        var result = ""
        var isMatch = false
        code = code.replace(MorseCharacters.WORDS_SPACING.value.toRegex(), MorseCharacters.COMMAS_WITH_SPACE.value)
        code = code.replace(MorseCharacters.LETTER_SPACING.value.toRegex(), MorseCharacters.COMMA.value)
        val codeSplit = code.split(MorseCharacters.COMMA.value.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (j in codeSplit.indices) {
            isMatch = false
            for (i in alphabetRussian.indices) {
                if (russianMorseCodes[i] == codeSplit[j]) {
                    isMatch = true
                    result += alphabetRussian[i]
                }
            }
            if (!isMatch) {
                // TODO: - Throw error here
            }
        }
        return result
    }

    fun russianToMorse(translates: CharArray): String {
        var morse = ""
        for (j in translates.indices) {
            val a = translates[j]
            val b = a.toString()
            for (i in alphabetRussian.indices) {
                if (j < translates.size - 1) {
                    if (alphabetRussian[i] == b && translates[j + 1].toString() != MorseCharacters.SPACE.value) {
                        morse += russianMorseCodes[i] + MorseCharacters.LETTER_SPACING.value
                    }
                    if (alphabetRussian[i] == b && translates[j + 1].toString() == MorseCharacters.SPACE.value) {
                        morse += russianMorseCodes[i]
                    }
                } else if (alphabetRussian[i] == b) {
                    morse += russianMorseCodes[i]
                }
            }
        }
        return morse
    }

    fun isRussian(text: String): Boolean {
        val pattern = Pattern.compile(
            "[" +  //начало списка допустимых символов
                    "а-яА-ЯёЁ" +  //буквы русского алфавита
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