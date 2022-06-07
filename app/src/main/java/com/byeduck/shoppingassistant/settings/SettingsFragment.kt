package com.byeduck.shoppingassistant.settings

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.byeduck.shoppingassistant.databinding.FragmentSettingsBinding
import com.byeduck.shoppingassistant.products.*

class SettingsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return with(binding) {
            priceSlider.setLabelFormatter(this@SettingsFragment::percentageLabelFormatter)
            brandSlider.setLabelFormatter(this@SettingsFragment::percentageLabelFormatter)
            ratingSlider.setLabelFormatter(this@SettingsFragment::percentageLabelFormatter)
            aiSlider.setLabelFormatter(this@SettingsFragment::percentageLabelFormatter)
            sharedPreferences = requireActivity().getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
            val savedPriceImportance =
                sharedPreferences.getInt(PRICE_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
            val savedBrandImportance =
                sharedPreferences.getInt(BRAND_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
            val savedRatingImportance =
                sharedPreferences.getInt(RATING_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
            val savedAiImportance =
                sharedPreferences.getInt(AI_IMPORTANCE_PREF_NAME, IMPORTANCE_DEFAULT_VALUE)
            priceSlider.value = savedPriceImportance.toFloat()
            brandSlider.value = savedBrandImportance.toFloat()
            ratingSlider.value = savedRatingImportance.toFloat()
            aiSlider.value = savedAiImportance.toFloat()
            settingSaveButton.setOnClickListener { saveSettings() }
            settingsCancelButton.setOnClickListener { cancelSettings() }
            this.root
        }
    }

    private fun percentageLabelFormatter(value: Float): String = "${value.toInt()} %"

    private fun cancelSettings() {
        // pass
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
    }
}