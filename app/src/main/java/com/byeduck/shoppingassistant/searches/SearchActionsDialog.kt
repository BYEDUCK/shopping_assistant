package com.byeduck.shoppingassistant.searches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.byeduck.shoppingassistant.databinding.SearchActionsDialogBinding

class SearchActionsDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SearchActionsDialogBinding.inflate(inflater, container, false)
        binding.searchActionsCloseButton.setOnClickListener { dismiss() }
        binding.searchActionsAssistButton.setOnClickListener {
            Toast.makeText(context, "Assist clicked", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}