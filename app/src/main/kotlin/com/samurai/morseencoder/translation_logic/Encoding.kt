package com.samurai.morseencoder.translation_logic

import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.MorseCharacters
import java.util.Locale
import java.util.regex.Pattern

class Encoding {

    var translationCompleted = false

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
        "=", "@", "+"
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
        "*******", ".*-*-*-*-*.", "-*-*-*.*.*.",
        "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-",
        ".*-*.*-*.*-", ".*.*-*-*.*.", "-*.*-*.*-*.",
        "-*.*.*-*.  ", ".*.*-*-*.*-", "-*-*-*.*.",
        "-*.*.*.*-", ".*-*-*.*-*.", ".*-*.*-*."
    )
    private val alphabetGerman = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
        "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
        "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+", "ä", "ö", "ü", "ß"
    )
    private val germanMorseCodes = arrayOf(
        ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
        ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".*-*-*.",
        "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
        "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
        "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", "*******",
        ".*-*-*-*-*.", "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-",
        ".*.*-*-*.*.", "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-",
        ".*-*-*.*-*.", ".*-*.*-*.", ".*-*.*-", "-*-*-*.", ".*.*-*-", ".*.*.*-*-*.*."
    )
    private val alphabetRussian = arrayOf(
        "а", "б", "в", "г", "д", "е", "ж", "з",
        "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
        "у", "ф", "х", "ц", "ш", "щ", "э", "ю", "я", "ь", "ы",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", "'",
        ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
        "=", "@", "+"
    )
    private val russianMorseCodes = arrayOf(
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
        "*******",
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
        ".*-*.*-*."
    )

    //. . . -   . . .   - - .       . - - -   . . . -   . - . - .
    //.*.*.*-***.*.*.***-*-*.*******.*-*-*-***.*.*.*-***.*-*.*-*.
    fun translate_to_code(text: String, lang: LanguageCode): String {
        val translates = text.lowercase(Locale.getDefault()).toCharArray()
        // Get language selected in settings
        var result = ""
        translationCompleted = false
        if (lang == LanguageCode.RUSSIAN && isRussian(text)) {
            result = rus_to_morse(translates)
            translationCompleted = true
        }
        if (lang == LanguageCode.GERMAN && isGerman(text)) {
            result = germ_to_morse(translates)
            translationCompleted = true
        }
        if (lang == LanguageCode.ENGLISH && isEnglish(text)) {
            result = eng_to_morse(translates)
            translationCompleted = true
        }
        return result
    }

    private fun isRussian(text: String): Boolean {
        var result = false
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
        result = matcher.matches()
        return result
    }

    private fun isGerman(text: String): Boolean {
        var result = false
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
        result = matcher.matches()
        return result
    }

    private fun isEnglish(text: String): Boolean {
        var result = false
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
        result = matcher.matches()
        return result
    }

    private fun eng_to_morse(translates: CharArray): String {
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

    private fun germ_to_morse(translates: CharArray): String {
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

    private fun rus_to_morse(translates: CharArray): String {
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
}