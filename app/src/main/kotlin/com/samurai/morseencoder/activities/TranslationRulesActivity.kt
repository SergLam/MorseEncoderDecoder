package com.samurai.morseencoder.activities

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TranslationRulesActivity : AppCompatActivity() {

    private lateinit var translationAlphabetGridLayout: GridLayout
    private lateinit var selectedLanguage: LanguageCode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation_rules)
        setupActionBar()
        initSubviews()
        getExtras()
        displayTranslationRules()
    }

    // region ACTION_BAR
    private fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_translation_rules)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.translation_english_rules_nav_bar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // endregion

    private fun initSubviews() {
        translationAlphabetGridLayout = findViewById(R.id.translation_alphabet_grid_layout)
    }

    private fun getExtras() {
        intent.extras?.let {
            val selectedLanguageValue = it.getString(languageCodeKey, LanguageCode.ENGLISH.value)!!
            selectedLanguage = LanguageCode.getByValueIgnoreCaseOrNull(selectedLanguageValue)
                ?: LanguageCode.ENGLISH
        }

    }

    private fun displayTranslationRules() {
        val strings = LanguageCode.getTranslationRules(code = selectedLanguage, context = this)
        val squareBracesPattern = "[\\[\\]]".toRegex()

        for ((index, string) in strings.withIndex()) {
            val translationTextView = TextView(this)
            translationTextView.setTextAppearance(R.style.TheoryTextStyle)
            val filteredString = string.replace(squareBracesPattern, "")
            translationTextView.text = filteredString
            val param = GridLayout.LayoutParams()
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT
            param.width = LinearLayout.LayoutParams.WRAP_CONTENT
            param.setGravity(Gravity.CENTER_HORIZONTAL)
            param.columnSpec = GridLayout.spec(index % 2)
            param.rowSpec = GridLayout.spec(index / 2)
            translationTextView.layoutParams = param
            translationAlphabetGridLayout.addView(translationTextView, index)
        }
    }

    companion object {
        const val languageCodeKey = "SELECTED_LANGUAGE_CODE"
    }
}