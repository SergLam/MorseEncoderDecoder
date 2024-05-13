package com.samurai.morseencoder.activities.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationMode
import com.samurai.morseencoder.services.storage.LocalStorageServiceImpl
import com.samurai.morseencoder.translation_logic.EnglishTranslationViewModel
import com.samurai.morseencoder.translation_logic.GermanTranslationViewModel
import com.samurai.morseencoder.translation_logic.RussianTranslationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private var selectedLanguage: LanguageCode
    var translationCompleted = true

    private val englishViewModel = EnglishTranslationViewModel()
    private val russianViewModel = RussianTranslationViewModel()
    private val germanViewModel = GermanTranslationViewModel()

    private val storage = LocalStorageServiceImpl(sharedPreferences = sharedPreferences)

    init {
        selectedLanguage = storage.getCurrentInputLanguage()
    }

    fun codeToText(code: String): String {
        // Get language selected in settings
        val result = when (selectedLanguage) {
            LanguageCode.RUSSIAN -> {
                russianViewModel.morseToRussian(code)
            }
            LanguageCode.GERMAN -> {
                germanViewModel.morseToGerman(code)
            }
            LanguageCode.ENGLISH -> {
                englishViewModel.morseToEnglish(code)
            }
        }
        translationCompleted = true
        return result
    }

    fun translateToCode(text: String): String {
        val translates = text.lowercase(Locale.getDefault()).toCharArray()
        // Get language selected in settings
        var result = ""
        translationCompleted = false
        when (selectedLanguage) {
            LanguageCode.RUSSIAN -> {
                if (russianViewModel.isRussian(text)) {
                    result = russianViewModel.russianToMorse(translates)
                    translationCompleted = true
                } else {
                    // TODO: - Throw an error
                }
            }
            LanguageCode.GERMAN -> {
                if (germanViewModel.isGerman(text)) {
                    result = germanViewModel.germanToMorse(translates)
                    translationCompleted = true
                } else {
                    // TODO: - Throw an error
                }
            }
            LanguageCode.ENGLISH -> {
                if (englishViewModel.isEnglish(text)) {
                    result = englishViewModel.englishToMorse(translates)
                    translationCompleted = true
                } else {
                    // TODO: - Throw an error
                }
            }
        }
        return result
    }

    fun saveTranslationMode(mode: TranslationMode) {
        storage.setActiveTranslationMode(mode)
    }

    fun getTranslationMode(): TranslationMode {
        return storage.getActiveTranslationMode()
    }
    fun getSelectedLanguage(): LanguageCode {
        selectedLanguage = storage.getCurrentInputLanguage()
        return selectedLanguage
    }

    fun getSelectedLanguageIndex(): Int {
        selectedLanguage = storage.getCurrentInputLanguage()
        return LanguageCode.selectedLanguageIndex(selectedLanguage)
    }

    fun saveSelectedLanguage(language: LanguageCode) {
        selectedLanguage = language
        storage.setCurrentInputLanguage(language)
    }
}