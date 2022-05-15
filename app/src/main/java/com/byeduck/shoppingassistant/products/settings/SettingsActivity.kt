package com.byeduck.shoppingassistant.products.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.byeduck.shoppingassistant.MainActivity
import com.byeduck.shoppingassistant.databinding.ActivitySettingsBinding
import com.byeduck.shoppingassistant.products.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        binding.priceSlider.setLabelFormatter(this::percentageLabelFormatter)
        binding.brandSlider.setLabelFormatter(this::percentageLabelFormatter)
        binding.ratingSlider.setLabelFormatter(this::percentageLabelFormatter)
        binding.aiSlider.setLabelFormatter(this::percentageLabelFormatter)
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val savedPriceImportance =
            sharedPreferences.getInt(PRICE_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        val savedBrandImportance =
            sharedPreferences.getInt(BRAND_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        val savedRatingImportance =
            sharedPreferences.getInt(RATING_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        val savedAiImportance =
            sharedPreferences.getInt(AI_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        binding.priceSlider.value = savedPriceImportance.toFloat()
        binding.brandSlider.value = savedBrandImportance.toFloat()
        binding.ratingSlider.value = savedRatingImportance.toFloat()
        binding.aiSlider.value = savedAiImportance.toFloat()
        binding.settingSaveButton.setOnClickListener { saveSettings() }
        binding.settingsCancelButton.setOnClickListener { cancelSettings() }
        setContentView(binding.root)
    }

    private fun percentageLabelFormatter(value: Float): String = "${value.toInt()} %"

    private fun cancelSettings() {
        goToMainActivity()
    }

    private fun saveSettings() {
        val priceImportance = binding.priceSlider.value.toInt()
        val brandImportance = binding.brandSlider.value.toInt()
        val ratingImportance = binding.ratingSlider.value.toInt()
        val aiImportance = binding.aiSlider.value.toInt()
        sharedPreferences.edit()
            .putInt(PRICE_IMPORTANCE_PREF_NAME, priceImportance)
            .putInt(BRAND_IMPORTANCE_PREF_NAME, brandImportance)
            .putInt(RATING_IMPORTANCE_PREF_NAME, ratingImportance)
            .putInt(AI_IMPORTANCE_PREF_NAME, aiImportance)
            .apply()
        goToMainActivity()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}