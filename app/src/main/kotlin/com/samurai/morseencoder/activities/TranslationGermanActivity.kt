package com.samurai.morseencoder.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.samurai.sysequsol.R

class TranslationGermanActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.translation_germ)
        val germ_back = findViewById<View>(R.id.btn_germ_back) as Button
        germ_back.setOnClickListener { onBackPressed() }
    }
}