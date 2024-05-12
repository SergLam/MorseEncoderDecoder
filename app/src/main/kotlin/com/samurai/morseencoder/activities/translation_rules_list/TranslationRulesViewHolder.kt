package com.samurai.morseencoder.activities.translation_rules_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.sysequsol.R

class TranslationRulesViewHolder(
    view: View,
    onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    private val languageIconImageView: ImageView
    private val languageNameTextView: TextView

    init {
        languageIconImageView = view.findViewById(R.id.rules_flag_image)
        languageNameTextView = view.findViewById(R.id.rules_language_name)
        view.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: TranslationLanguageListItem) {
        languageIconImageView.setImageResource(model.flagResId)
        val countryName = languageNameTextView.context.getString(model.countryNameResId)
        languageNameTextView.text = countryName
    }
}