package com.samurai.morseencoder.activities.translation_rules_list

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samurai.morseencoder.activities.translation_rules.TranslationRulesActivity
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslationRulesListActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation_rules_list)
        setupActionBar()
        initSubviews()
    }

    private fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_translation_rules_list)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setSubtitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.translation_rules_list_nav_bar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
    }

    private fun initSubviews() {
        recyclerView = findViewById(R.id.translation_rules_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TranslationRulesAdapter(
            items = TranslationLanguageListItem.allRules,
            onItemClick = {
                startTranslationRulesActivity(it.code)
            })
    }

    private fun startTranslationRulesActivity(code: LanguageCode) {
        val intent = Intent(this, TranslationRulesActivity::class.java)
        intent.putExtra(TranslationRulesActivity.languageCodeKey, code.value)
        startActivity(intent)
    }
}