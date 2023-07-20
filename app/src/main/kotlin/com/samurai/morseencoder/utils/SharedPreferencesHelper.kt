package com.samurai.morseencoder.utils

import android.content.SharedPreferences

fun SharedPreferences.edit(actions: SharedPreferences.Editor.() -> Unit) {
    with (edit()) {
        actions(this)
        apply()
    }
}