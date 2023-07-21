package com.samurai.morseencoder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.samurai.morseencoder.activities.TranslationRulesActivity
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.sysequsol.R

class TranslationRulesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_translation_rules, container, false)
        val buttonEnglish = view.findViewById<Button>(R.id.tr_btn_eng)
        buttonEnglish.setOnClickListener {
            startTranslationRulesActivity(code = LanguageCode.ENGLISH)
        }
        val buttonRussian = view.findViewById<Button>(R.id.tr_btn_rus)
        buttonRussian.setOnClickListener {
            startTranslationRulesActivity(code = LanguageCode.RUSSIAN)
        }
        val buttonGerman = view.findViewById<Button>(R.id.tr_btn_germ)
        buttonGerman.setOnClickListener {
            startTranslationRulesActivity(code = LanguageCode.GERMAN)
        }
        return view
    }

    private fun startTranslationRulesActivity(code: LanguageCode) {
        val intent = Intent(activity, TranslationRulesActivity::class.java)
        intent.putExtra(TranslationRulesActivity.languageCodeKey, code.value)
        startActivity(intent)
    }
}