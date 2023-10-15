package com.samurai.morseencoder.fragments.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.fragments.translation_rules.TranslationRulesViewHolder
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.sysequsol.R

class SettingsLanguageAdapter(
    private val items: List<SettingsLanguageListItem>,
    private val onItemClick: (TranslationLanguageListItem) -> Unit
): RecyclerView.Adapter<SettingsLanguageViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SettingsLanguageViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.settings_language_list_item, viewGroup, false)
        return SettingsLanguageViewHolder(view) {
            onItemClick(items[it].item)
        }
    }

    override
    fun onBindViewHolder(viewHolder: SettingsLanguageViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount() = items.size
}