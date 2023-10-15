package com.samurai.morseencoder.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.samurai.sysequsol.R

data class TranslationLanguageListItem(
    @DrawableRes val flagResId: Int,
    @StringRes val countryNameResId: Int,
    val code: LanguageCode
) {

    companion object {
        val allRules: List<TranslationLanguageListItem> = listOf(
            TranslationLanguageListItem(
                flagResId = com.idmikael.flags_iso.R.drawable.gb,
                countryNameResId = R.string.translation_list_english_title,
                code = LanguageCode.ENGLISH
            ),
            TranslationLanguageListItem(
                flagResId = com.idmikael.flags_iso.R.drawable.ru,
                countryNameResId = R.string.translation_list_russian_title,
                code = LanguageCode.RUSSIAN
            ),
            TranslationLanguageListItem(
                flagResId = com.idmikael.flags_iso.R.drawable.de,
                countryNameResId = R.string.translation_list_german_title,
                code = LanguageCode.GERMAN
            )
        )
    }
}
