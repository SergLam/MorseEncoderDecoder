package com.samurai.morseencoder.fragments.translation

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationMode
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslationFragment : Fragment() {

    private val viewModel: TranslationViewModel by viewModels()

    private lateinit var flagImageView: ImageView
    private lateinit var languageInputEditText: EditText
    private lateinit var morseEditText: EditText
    private lateinit var translateButton: MaterialButton
    private lateinit var modeRadioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main_translation, container, false)
        // Set filter to morse text field
        initSubviews(view)
        setTranslationModeChangeListener()
        setTranslateButtonClickListener()
        return view
    }

    override fun onResume() {
        super.onResume()
        setFlagImage()
    }

    private fun initSubviews(view: View) {
        morseEditText = view.findViewById(R.id.morse_text)
        languageInputEditText = view.findViewById(R.id.eng_text)
        flagImageView = view.findViewById(R.id.flag_image)
        translateButton = view.findViewById(R.id.translate_button)
        modeRadioGroup = view.findViewById(R.id.radio_mode)
    }

    private fun setTranslationModeChangeListener() {
        modeRadioGroup.setOnCheckedChangeListener { _, checkedID ->
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
    }

    private fun setTranslateButtonClickListener() {
        translateButton.setOnClickListener {
            val mode = viewModel.getTranslationMode()
            val language = viewModel.getSelectedLanguage()
            val message: String = languageInputEditText.text.toString()
            val messageMorse: String = morseEditText.text.toString()
            when (mode) {
                TranslationMode.ENCODE -> {
                    val result = viewModel.encoding.translateToCode(message, language)
                    if (viewModel.encoding.translationCompleted) {
                        morseEditText.setText(result)
                    } else showErrorToast(R.string.mess_encode)
                }
                TranslationMode.DECODE -> {
                    val result: String = viewModel.decoding.code_to_text(messageMorse, language)
                    if (viewModel.decoding.translationCompleted) {
                        languageInputEditText.setText(result)
                    } else showErrorToast(R.string.mess_decode)
                }
            }
        }
    }

    private fun showErrorToast(@StringRes messageStringId: Int) {
        Toast.makeText(
            activity,
            resources.getString(messageStringId),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setFlagImage() {
        when(viewModel.getSelectedLanguage()) {
            LanguageCode.ENGLISH -> {
                flagImageView.setImageResource(com.idmikael.flags_iso.R.drawable.gb)
            }
            LanguageCode.GERMAN -> {
                flagImageView.setImageResource(com.idmikael.flags_iso.R.drawable.de)
            }
            LanguageCode.RUSSIAN -> {
                flagImageView.setImageResource(com.idmikael.flags_iso.R.drawable.ru)
            }
        }
    }
}