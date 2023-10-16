package com.samurai.morseencoder.fragments.translation_rules

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.activities.TranslationRulesActivity
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslationRulesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_translation_rules, container, false)
        recyclerView = view.findViewById(R.id.translation_rules_list)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = TranslationRulesAdapter(
            items = TranslationLanguageListItem.allRules,
            onItemClick = {
            startTranslationRulesActivity(it.code)
        })
        return view
    }

    private fun startTranslationRulesActivity(code: LanguageCode) {
        val intent = Intent(activity, TranslationRulesActivity::class.java)
        intent.putExtra(TranslationRulesActivity.languageCodeKey, code.value)
        startActivity(intent)
    }
}