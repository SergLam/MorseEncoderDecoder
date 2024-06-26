package com.samurai.morseencoder.activities.translation_rules

import android.graphics.Color
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
        initSubviews()
        getExtras()
        displayTranslationRules()
    }

    // region ACTION_BAR

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // endregion

    private fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_translation_rules)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setSubtitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar?.title = LanguageCode.getTranslationRulesNavBarTitle(selectedLanguage, context = this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
    }

    private fun initSubviews() {
        translationAlphabetGridLayout = findViewById(R.id.translation_alphabet_grid_layout)
    }

    private fun getExtras() {
        intent.extras?.let {
            val selectedLanguageValue = it.getString(languageCodeKey, LanguageCode.ENGLISH.value)!!
            selectedLanguage = LanguageCode.getByValueIgnoreCaseOrNull(selectedLanguageValue)
                ?: LanguageCode.ENGLISH
            setupActionBar()
        }

    }

    private fun displayTranslationRules() {
        val strings = LanguageCode.getTranslationRules(code = selectedLanguage, context = this)
        val squareBracesPattern = "[\\[\\]]".toRegex()

        for ((index, string) in strings.withIndex()) {
            val translationTextView = TextView(this)
            translationTextView.setTextAppearance(R.style.BodyTextStyle)
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