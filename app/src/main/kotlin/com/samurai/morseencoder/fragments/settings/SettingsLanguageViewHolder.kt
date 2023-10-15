package com.samurai.morseencoder.fragments.settings

import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.sysequsol.R

class SettingsLanguageViewHolder(
    view: View,
    onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val languageIconImageView: ImageView
    private val languageNameTextView: TextView
    private val languageSelectedRadioButton: RadioButton
    init {
        languageIconImageView = view.findViewById(R.id.rules_flag_image)
        languageNameTextView = view.findViewById(R.id.rules_language_name)
        languageSelectedRadioButton = view.findViewById(R.id.settings_language_radio_btn)
        view.setOnClickListener {
            languageSelectedRadioButton.isChecked = true
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: SettingsLanguageListItem) {
        languageIconImageView.setImageResource(model.item.flagResId)
        val countryName = languageNameTextView.context.getString(model.item.countryNameResId)
        languageNameTextView.text = countryName
        languageSelectedRadioButton.isChecked = model.selected
    }
}