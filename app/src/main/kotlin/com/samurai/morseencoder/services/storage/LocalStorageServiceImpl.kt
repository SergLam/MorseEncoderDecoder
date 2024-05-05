package com.samurai.morseencoder.services.storage


import android.content.SharedPreferences
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationMode
import com.samurai.morseencoder.utils.edit

class LocalStorageServiceImpl(
    private val sharedPreferences: SharedPreferences
): LocalStorageService  {

    override
    fun getActiveTranslationMode(): TranslationMode {
        val string = sharedPreferences.getString(LocalStorageKey.TRANSLATION_MODE.value, TranslationMode.ENCODE.value) ?: TranslationMode.ENCODE.value
        return TranslationMode.getByValue(string) ?: TranslationMode.ENCODE
    }

    override
    fun setActiveTranslationMode(mode: TranslationMode) {
        sharedPreferences.edit {
            this.putString(LocalStorageKey.TRANSLATION_MODE.value, mode.value)
        }
    }

    override
    fun getCurrentInputLanguage(): LanguageCode {
        val string = sharedPreferences.getString(LocalStorageKey.TRANSLATION_LANGUAGE.value, LanguageCode.ENGLISH.value) ?: LanguageCode.ENGLISH.value
        return LanguageCode.getByValueIgnoreCaseOrNull(string) ?: LanguageCode.ENGLISH
    }

    override
    fun setCurrentInputLanguage(language: LanguageCode) {
        sharedPreferences.edit {
            this.putString(LocalStorageKey.TRANSLATION_LANGUAGE.value, language.value)
        }
    }
}