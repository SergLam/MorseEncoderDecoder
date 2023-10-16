package com.samurai.morseencoder.fragments.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.samurai.sysequsol.R


class SettingsLanguageAdapter(
    private var items: List<SettingsLanguageListItem>,
    private val onItemClick: (SettingsLanguageListItem) -> Unit
): RecyclerView.Adapter<SettingsLanguageViewHolder>() {

    private var mCheckedPosition = items.indexOfFirst { it.selected }

    fun setData(items: List<SettingsLanguageListItem>) {
        this.items = items
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SettingsLanguageViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.settings_language_list_item, viewGroup, false)
        return SettingsLanguageViewHolder(view) {
            mCheckedPosition = it
            onItemClick(items[it])
        }
    }

    override
    fun onBindViewHolder(viewHolder: SettingsLanguageViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount() = items.size
}