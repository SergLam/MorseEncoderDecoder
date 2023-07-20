package com.samurai.morseencoder.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.samurai.sysequsol.R

class TranslationRussianActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.translation_rus)
        val rus_back = findViewById<View>(R.id.btn_rus_back) as Button
        rus_back.setOnClickListener { onBackPressed() }
    }
}