package com.samurai.morseencoder.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.samurai.morseencoder.activities.TranslationEnglishActivity
import com.samurai.morseencoder.activities.TranslationGermanActivity
import com.samurai.morseencoder.activities.TranslationRussianActivity
import com.samurai.sysequsol.R

class TranslationRulesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.rules, container, false)
        val buttonEnglish = view.findViewById<Button>(R.id.tr_btn_eng)
        buttonEnglish.setOnClickListener {
            val intent = Intent(activity, TranslationEnglishActivity::class.java)
            startActivity(intent)
        }
        val buttonRussian = view.findViewById<Button>(R.id.tr_btn_rus)
        buttonRussian.setOnClickListener {
            val intent = Intent(activity, TranslationRussianActivity::class.java)
            startActivity(intent)
        }
        val buttonGerman = view.findViewById<Button>(R.id.tr_btn_germ)
        buttonGerman.setOnClickListener {
            val intent = Intent(activity, TranslationGermanActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}