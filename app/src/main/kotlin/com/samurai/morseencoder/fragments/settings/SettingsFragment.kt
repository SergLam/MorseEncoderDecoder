package com.samurai.morseencoder.fragments.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.services.storage.LocalStorageKey
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SettingsLanguageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_translation_rules, container, false)
        adapter = SettingsLanguageAdapter(
            items = viewModel.listDataSource,
            onItemClick = {
                setLanguage(it)
            })
        recyclerView = view.findViewById(R.id.translation_rules_list)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        return view
    }

    private fun setLanguage(model: SettingsLanguageListItem) {
        viewModel.saveSelectedLanguage(model.item.code)
        adapter.setData(viewModel.listDataSource)
        adapter.notifyDataSetChanged()
    }
}