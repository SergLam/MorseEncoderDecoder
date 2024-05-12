package com.samurai.morseencoder.activities.translation_rules_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.sysequsol.R

class TranslationRulesAdapter(
    private val items: List<TranslationLanguageListItem>,
    private val onItemClick: (TranslationLanguageListItem) -> Unit
) : RecyclerView.Adapter<TranslationRulesViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TranslationRulesViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.translation_rules_list_item, viewGroup, false)
        return TranslationRulesViewHolder(view) {
            onItemClick(items[it])
        }
    }

    override fun onBindViewHolder(viewHolder: TranslationRulesViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
