package com.samurai.morseencoder.fragments.translation

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationMode
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslationFragment : Fragment() {

    private val viewModel: TranslationViewModel by viewModels()

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
                    val result = source[i].toString() == "." || source[i].toString() == "-" || source[i].toString() == "*"
                    if (!result) {
                        return ""
                    }
                }
                return ""
            }
        }
        morse.filters = arrayOf(filter)
        // Set radio group listener
        val radGrp: RadioGroup = view.findViewById<View>(R.id.radio_mode) as RadioGroup
        radGrp.setOnCheckedChangeListener { _, checkedID ->
            when (checkedID) {
                R.id.radio_encode -> {
                    viewModel.saveTranslationMode(TranslationMode.ENCODE)
                }
                R.id.radio_decode -> {
                    viewModel.saveTranslationMode(TranslationMode.DECODE)
                }
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
                        val result = viewModel.encoding.translateToCode(message, language)
                        if (viewModel.encoding.translationCompleted) {
                            code.setText(result)
                        } else Toast.makeText(
                            activity,
                            resources.getString(R.string.mess_encode),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TranslationMode.DECODE -> {
                        val result: String = viewModel.decoding.code_to_text(messageMorse, language)
                        if (viewModel.decoding.translationCompleted) {
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
        setFlag(view)
        return view
    }

    private fun setFlag(view: View) {
        val flagButton = view.findViewById<Button>(R.id.btn_flag)
        when(viewModel.getSelectedLanguage()) {
            LanguageCode.ENGLISH -> {
                flagButton.setBackgroundResource(com.idmikael.flags_iso.R.drawable.gb)
            }
            LanguageCode.GERMAN -> {
                flagButton.setBackgroundResource(com.idmikael.flags_iso.R.drawable.de)
            }
            LanguageCode.RUSSIAN -> {
                flagButton.setBackgroundResource(com.idmikael.flags_iso.R.drawable.ru)
            }
        }
    }
}