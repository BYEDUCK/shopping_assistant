package com.byeduck.shoppingassistant.products.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.byeduck.shoppingassistant.MainActivity
import com.byeduck.shoppingassistant.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private val prefFileName = "byeduck_sa_preferences"
    private val priceImportancePrefName = "price_importance"
    private val brandImportancePrefName = "brand_importance"
    private val ratingImportancePrefName = "rating_importance"
    private val importanceDefaultValue = 0

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        binding.priceSlider.setLabelFormatter(this::percentageLabelFormatter)
        binding.brandSlider.setLabelFormatter(this::percentageLabelFormatter)
        binding.ratingSlider.setLabelFormatter(this::percentageLabelFormatter)
        sharedPreferences = getSharedPreferences(prefFileName, MODE_PRIVATE)
        val savedPriceImportance =
            sharedPreferences.getInt(priceImportancePrefName, importanceDefaultValue)
        val savedBrandImportance =
            sharedPreferences.getInt(brandImportancePrefName, importanceDefaultValue)
        val savedRatingImportance =
            sharedPreferences.getInt(ratingImportancePrefName, importanceDefaultValue)
        binding.priceSlider.value = savedPriceImportance.toFloat()
        binding.brandSlider.value = savedBrandImportance.toFloat()
        binding.ratingSlider.value = savedRatingImportance.toFloat()
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
        sharedPreferences.edit()
            .putInt(priceImportancePrefName, priceImportance)
            .putInt(brandImportancePrefName, brandImportance)
            .putInt(ratingImportancePrefName, ratingImportance)
            .apply()
        goToMainActivity()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}