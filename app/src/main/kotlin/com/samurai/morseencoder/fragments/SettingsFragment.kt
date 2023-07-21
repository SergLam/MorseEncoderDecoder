package com.samurai.morseencoder.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.samurai.morseencoder.utils.edit
import com.samurai.sysequsol.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    fun set_lang(view: View) {
        requireContext().getSharedPreferences("flag", Context.MODE_PRIVATE).let { preferences ->
            preferences.edit {
                val radio: RadioGroup = view.findViewById(R.id.radioGroup)
                val radioButtonID: Int = radio.checkedRadioButtonId
                val radioBut: RadioButton = view.findViewById(radioButtonID)
                val lang = radioBut.getTag() as String
                this.putString("lang", lang)
                this.apply()
            }
        }
    }
}