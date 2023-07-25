package com.samurai.morseencoder.fragments.translation_rules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samurai.sysequsol.R

class TranslationRulesAdapter(
    private val items: List<TranslationRulesListItem>,
    private val onItemClick: (TranslationRulesListItem) -> Unit
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
