package com.samurai.morseencoder.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.samurai.sysequsol.R

class TranslationEnglishActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.translation_eng)
        val eng_back = findViewById<View>(R.id.btn_eng_back) as Button
        eng_back.setOnClickListener { onBackPressed() }
    }
}