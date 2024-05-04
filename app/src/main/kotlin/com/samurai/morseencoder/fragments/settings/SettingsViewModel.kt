package com.samurai.morseencoder.fragments.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.morseencoder.services.storage.LocalStorageServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private var selectedLanguage: LanguageCode

    var listDataSource: List<SettingsLanguageListItem>

    private val storage = LocalStorageServiceImpl(sharedPreferences = sharedPreferences)

    init {
        selectedLanguage = storage.getCurrentInputLanguage()
        listDataSource = SettingsLanguageListItem.buildModelsList(TranslationLanguageListItem.allRules, selectedLanguage)
    }

    fun getSelectedLanguage(): LanguageCode {
        return selectedLanguage
    }

    fun saveSelectedLanguage(language: LanguageCode) {
        selectedLanguage = language
        listDataSource.map {
            it.selected = it.item.code == language
        }
        storage.setCurrentInputLanguage(language)
    }
}