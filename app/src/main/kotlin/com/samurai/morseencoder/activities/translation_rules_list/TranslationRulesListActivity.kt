package com.samurai.morseencoder.activities.translation_rules_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslationRulesListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}