package com.samurai.morseencoder.fragments.settings

import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.sysequsol.R
import java.text.FieldPosition

class SettingsLanguageViewHolder(
    view: View,
    onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val languageIconImageView: ImageView
    private val languageNameTextView: TextView
    val radioButton: RadioButton

    init {
        languageIconImageView = view.findViewById(R.id.settings_flag_image)
        languageNameTextView = view.findViewById(R.id.settings_language_name)
        radioButton = view.findViewById(R.id.settings_language_radio_btn)
        view.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: SettingsLanguageListItem) {
        languageIconImageView.setImageResource(model.item.flagResId)
        val countryName = languageNameTextView.context.getString(model.item.countryNameResId)
        languageNameTextView.text = countryName
        radioButton.isChecked = model.selected
    }
}