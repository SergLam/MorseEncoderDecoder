package com.samurai.morseencoder.fragments.settings

import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem

data class SettingsLanguageListItem(
    val index: Int,
    val item: TranslationLanguageListItem,
    var selected: Boolean
    ) {

    companion object {

        fun buildModelsList(items: List<TranslationLanguageListItem>,
                            selectedLanguage: LanguageCode): List<SettingsLanguageListItem> {
            return items.mapIndexed { index, element ->
                val isSelected = element.code == selectedLanguage
                SettingsLanguageListItem(index = index, item = element, selected = isSelected)
            }
        }
    }
}