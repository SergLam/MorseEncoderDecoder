package com.samurai.morseencoder.fragments.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.morseencoder.services.storage.LocalStorageKey
import com.samurai.morseencoder.services.storage.LocalStorageServiceImpl
import org.intellij.lang.annotations.Language

class SettingsViewModel constructor(
    context: Context
): ViewModel() {

    private var selectedLanguage: LanguageCode

    var listDataSource: List<SettingsLanguageListItem>

    private val storage = LocalStorageServiceImpl(
        sharedPreferences = context.getSharedPreferences(LocalStorageKey.APP_PREFERENCES, MODE_PRIVATE)
    )

    init {
        selectedLanguage = storage.getCurrentInputLanguage()
        listDataSource = SettingsLanguageListItem.buildModelsList(TranslationLanguageListItem.allRules, selectedLanguage)
    }

    fun getSelectedLanguage(): LanguageCode {
        return selectedLanguage
    }

    fun saveSelectedLanguage(language: LanguageCode) {
        selectedLanguage = language
        listDataSource.first { it.item.code == language }.selected = true
        storage.setCurrentInputLanguage(language)
    }
}