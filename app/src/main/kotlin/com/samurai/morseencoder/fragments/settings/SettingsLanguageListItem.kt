package com.samurai.morseencoder.fragments.settings

import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem

data class SettingsLanguageListItem(
    val item: TranslationLanguageListItem,
    var selected: Boolean
    ) {

    companion object {

        fun buildModelsList(items: List<TranslationLanguageListItem>,
                            selectedLanguage: LanguageCode): List<SettingsLanguageListItem> {
            return items.map {
                val isSelected = it.code == selectedLanguage
                SettingsLanguageListItem(item = it, selected = isSelected)
            }
        }
    }
}