package com.samurai.morseencoder.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.samurai.morseencoder.activities.translation_rules.TranslationRulesActivity
import com.samurai.morseencoder.activities.translation_rules_list.TranslationRulesAdapter
import com.samurai.morseencoder.models.LanguageCode
import com.samurai.morseencoder.models.TranslationLanguageListItem
import com.samurai.morseencoder.models.TranslationMode
import com.samurai.sysequsol.R
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.lang.RuntimeException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: TranslationViewModel by viewModels()
    private lateinit var reviewManager: ReviewManager

    private lateinit var languageInputEditText: EditText
    private lateinit var morseEditText: EditText
    private lateinit var translateButton: MaterialButton
    private lateinit var modeRadioGroup: RadioGroup
    private lateinit var flagImageView: ImageView
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reviewManager = ReviewManagerFactory.create(this)
        initSubviews()
        setTranslationModeChangeListener()
        setTranslateButtonClickListener()
        setLanguagePickerAction(this)
        setFlagImage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chooses the "Settings" item. Show the app settings UI.
            true
        }

        R.id.action_favorite -> {
            // User chooses the "Favorite" action. Mark the current item as a
            // favorite.
            requestAppReview()
            true
        }

        else -> {
            // The user's action isn't recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun initSubviews() {
        setSupportActionBar(findViewById(R.id.main_toolbar))
        morseEditText = findViewById(R.id.morse_text)
        languageInputEditText = findViewById(R.id.eng_text)
        translateButton = findViewById(R.id.translate_button)
        modeRadioGroup = findViewById(R.id.radio_mode)
        flagImageView = findViewById(R.id.flag_image)
        recyclerView = findViewById(R.id.translation_rules_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TranslationRulesAdapter(
            items = TranslationLanguageListItem.allRules,
            onItemClick = {
                startTranslationRulesActivity(it.code)
            })
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
            this,
            resources.getString(messageStringId),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun requestAppReview() {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = reviewManager.launchReviewFlow(this, reviewInfo)
                flow.addOnCompleteListener { result ->
                    val bundle = Bundle()
                    bundle.putString("is_canceled", result.isCanceled.toString())
                    bundle.putString("is_complete", result.isComplete.toString())
                    bundle.putString("is_successful", result.isSuccessful.toString())
                    Firebase.analytics.logEvent("app_review_completion", bundle)
                }
            } else {
                @ReviewErrorCode
                val code = (task.exception as ReviewException).errorCode
                Firebase.crashlytics.recordException(RuntimeException("Review Error $code"))
            }
        }
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

    private fun setLanguagePickerAction(context: Context) {
        flagImageView.setOnClickListener{
            val singleItems = LanguageCode.values().map{
                LanguageCode.displayValue(it, context)
            }.toTypedArray()
            val checkedItem = viewModel.getSelectedLanguageIndex()
            MaterialAlertDialogBuilder(context)
                .setTitle(resources.getString(R.string.text_rules))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                    val selectedPosition: Int =
                        (dialog as AlertDialog).listView.checkedItemPosition
                    viewModel.saveSelectedLanguage(LanguageCode.values()[selectedPosition])
                    setFlagImage()
                    dialog.dismiss()
                }
                .setSingleChoiceItems(singleItems, checkedItem) { _, _ -> }
                .show()

        }
    }

    private fun startTranslationRulesActivity(code: LanguageCode) {
        val intent = Intent(this, TranslationRulesActivity::class.java)
        intent.putExtra(TranslationRulesActivity.languageCodeKey, code.value)
        startActivity(intent)
    }
}