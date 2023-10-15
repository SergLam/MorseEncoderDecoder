package com.samurai.morseencoder.fragments.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.samurai.morseencoder.utils.edit
import com.samurai.sysequsol.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.fragments.translation_rules.TranslationRulesAdapter
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem
import org.intellij.lang.annotations.Language

class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this)[SettingsViewModel::class.java]
    }

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
                setLanguage(it.code)
            })
        recyclerView = view.findViewById(R.id.translation_rules_list)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        return view
    }

    private fun setLanguage(language: LanguageCode) {
        viewModel.saveSelectedLanguage(language)
        adapter.notifyDataSetChanged()
    }
}