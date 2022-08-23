package com.byeduck.shoppingassistant.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.byeduck.shoppingassistant.MainActivity
import com.byeduck.shoppingassistant.databinding.ActivitySettingsBinding
import com.byeduck.shoppingassistant.products.*

class SettingsActivity : AppCompatActivity() {

    private val trustedBrandsSeparator = ":"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var brandsAdapter: BrandsListElementAdapter
    private var trustedBrands: Set<String> = emptySet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        brandsAdapter = BrandsListElementAdapter(this) {
            trustedBrands = trustedBrands - it
            updateTrustedBrandsDisplayName()
        }
        sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        val savedPriceImportance =
            sharedPreferences.getInt(PRICE_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        val savedBrandImportance =
            sharedPreferences.getInt(BRAND_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        val savedRatingImportance =
            sharedPreferences.getInt(RATING_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        val savedAiImportance =
            sharedPreferences.getInt(AI_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
        trustedBrands = parseTrustedBrands(
            sharedPreferences.getString(TRUSTED_BRANDS_PREF_NAME, "") ?: ""
        )
        binding.apply {
            priceSlider.setLabelFormatter(this@SettingsActivity::percentageLabelFormatter)
            brandSlider.setLabelFormatter(this@SettingsActivity::percentageLabelFormatter)
            ratingSlider.setLabelFormatter(this@SettingsActivity::percentageLabelFormatter)
            aiSlider.setLabelFormatter(this@SettingsActivity::percentageLabelFormatter)
            addTrustedBrandButton.setOnClickListener { addTrustedBrand() }
            trustedBrands.adapter = brandsAdapter
            trustedBrands.layoutManager = LinearLayoutManager(applicationContext)
            priceSlider.value = savedPriceImportance.toFloat()
            brandSlider.value = savedBrandImportance.toFloat()
            ratingSlider.value = savedRatingImportance.toFloat()
            aiSlider.value = savedAiImportance.toFloat()
            settingSaveButton.setOnClickListener { saveSettings() }
            settingsCancelButton.setOnClickListener { cancelSettings() }
        }
        updateTrustedBrandsDisplayName()
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
        val trustedBrandsSerialized = trustedBrands.joinToString(trustedBrandsSeparator)
        sharedPreferences.edit()
            .putInt(PRICE_IMPORTANCE_PREF_NAME, priceImportance)
            .putInt(BRAND_IMPORTANCE_PREF_NAME, brandImportance)
            .putInt(RATING_IMPORTANCE_PREF_NAME, ratingImportance)
            .putInt(AI_IMPORTANCE_PREF_NAME, aiImportance)
            .putString(TRUSTED_BRANDS_PREF_NAME, trustedBrandsSerialized)
            .apply()
        goToMainActivity()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateTrustedBrandsDisplayName() {
        brandsAdapter.submitList(trustedBrands.toMutableList())
    }

    private fun parseTrustedBrands(brandText: String): Set<String> =
        if (brandText.isBlank()) emptySet() else brandText.split(trustedBrandsSeparator).toSet()

    private fun addTrustedBrand() {
        trustedBrands = trustedBrands + binding.trustedBrandToAdd.text.toString()
        binding.trustedBrandToAdd.text.clear()
        updateTrustedBrandsDisplayName()
    }
}