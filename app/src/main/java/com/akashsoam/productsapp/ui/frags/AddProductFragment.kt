package com.akashsoam.productsapp.ui.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akashsoam.productsapp.databinding.FragmentAddProductBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

