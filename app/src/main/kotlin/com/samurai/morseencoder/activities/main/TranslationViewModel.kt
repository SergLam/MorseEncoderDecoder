package com.samurai.morseencoder.activities.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationMode
import com.samurai.morseencoder.services.storage.LocalStorageServiceImpl
import com.samurai.morseencoder.translation_logic.Decoding
import com.samurai.morseencoder.translation_logic.Encoding
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private var selectedLanguage: LanguageCode

    var encoding = Encoding()
    var decoding = Decoding()

    private val storage = LocalStorageServiceImpl(sharedPreferences = sharedPreferences)

    init {
        selectedLanguage = storage.getCurrentInputLanguage()
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