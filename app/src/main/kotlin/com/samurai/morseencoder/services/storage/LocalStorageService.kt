package com.samurai.morseencoder.services.storage

import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationMode

interface LocalStorageService {

    fun getActiveTranslationMode(): TranslationMode

    fun setActiveTranslationMode(mode: TranslationMode)

    fun getCurrentInputLanguage(): LanguageCode

    fun setCurrentInputLanguage(language: LanguageCode)
}