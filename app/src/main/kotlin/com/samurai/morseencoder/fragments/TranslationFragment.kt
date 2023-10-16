package com.samurai.morseencoder.fragments

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationMode
import com.samurai.morseencoder.translation_logic.Decoding
import com.samurai.morseencoder.translation_logic.Encoding
import com.samurai.morseencoder.utils.edit
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslationFragment : Fragment() {

    var obj_encode = Encoding()
    var obj_decode: Decoding = Decoding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main_translation, container, false)
        // Set filter to morse text field
        val morse: EditText = view.findViewById(R.id.morse_text)
        val filter: InputFilter = object : InputFilter {
            override fun filter(
                source: CharSequence, start: Int, end: Int,
                dest: Spanned, dstart: Int, dend: Int
            ): CharSequence {
                for (i in start until end) {
                    // Your condition here
                    val result = Character.toString(source[i]) == "." || Character.toString(
                        source[i]
                    ) == "-" || Character.toString(source[i]) == "*"
                    if (!result) {
                        return ""
                    }
                }
                return ""
            }
        }
        morse.filters = arrayOf(filter)
        // Set radiogroup listener
        val radGrp: RadioGroup = view.findViewById<View>(R.id.radio_mode) as RadioGroup
        val checkedRadioButtonID: Int = radGrp.checkedRadioButtonId
        radGrp.setOnCheckedChangeListener { arg0, id ->
            when (id) {
                R.id.radio_encode -> get_mode(view)
                R.id.radio_decode -> get_mode(view)
                else -> {}
            }
        }
        // Connect translation function to translate button
        val button = view.findViewById<Button>(R.id.trans_btn)
        button.setOnClickListener {
            val text: EditText = view.findViewById(R.id.eng_text)
            val code: EditText = view.findViewById(R.id.morse_text)
            requireContext().getSharedPreferences("flag", Context.MODE_PRIVATE).let { preferences ->
                val modeRaw: String = preferences.getString("mode", "")!!
                val mode = TranslationMode.getByValue(modeRaw) ?: TranslationMode.ENCODE
                val langRaw: String = preferences.getString("lang", "")!!
                val language = LanguageCode.getByValueIgnoreCaseOrNull(langRaw) ?: LanguageCode.ENGLISH

                val message: String = text.text.toString()
                val messageMorse: String = code.text.toString()
                when (mode) {
                    TranslationMode.ENCODE -> {
                        val result = obj_encode.translate_to_code(message, language)
                        if (obj_encode.translationCompleted) {
                            code.setText(result)
                        } else Toast.makeText(
                            activity,
                            resources.getString(R.string.mess_encode),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TranslationMode.DECODE -> {
                        val result: String = obj_decode.code_to_text(messageMorse, language)
                        if (obj_decode.translationCompleted) {
                            text.setText(result)
                        } else Toast.makeText(
                            activity,
                            resources.getString(R.string.mess_decode),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        // Put preferences to flag choise
        requireContext().getSharedPreferences("flag", Context.MODE_PRIVATE).let { preferences ->
            preferences.edit {
                this.putString("lang", "english")
                this.putString("mode", "encode")
                this.apply()
            }
        }

        set_flag(view)
        return view
    }

    fun set_flag(view: View): View {
        val flagButton = view.findViewById<Button>(R.id.btn_flag)
        requireContext().getSharedPreferences("flag", Context.MODE_PRIVATE).let { preferences ->
            val lang: String? = preferences.getString("lang", "")
            if (lang == "english") {
                flagButton.setBackgroundResource(com.idmikael.flags_iso.R.drawable.gb)
            }
            if (lang == "russian") {
                flagButton.setBackgroundResource(com.idmikael.flags_iso.R.drawable.ru)
            }
            if (lang == "german") {
                flagButton.setBackgroundResource(com.idmikael.flags_iso.R.drawable.de)
            }
        }
        return view
    }

    fun get_mode(view: View) {
        val radio: RadioGroup = view.findViewById(R.id.radio_mode)
        val radioButtonID: Int = radio.checkedRadioButtonId
        val radioBut: RadioButton = view.findViewById(radioButtonID)
        val mode = radioBut.tag as String
        requireContext().getSharedPreferences("flag", Context.MODE_PRIVATE).let { preferences ->
            preferences.edit {
                this.putString("mode", mode)
            }
        }
        //Toast.makeText(getActivity(), mode, Toast.LENGTH_LONG).show();
    }
}