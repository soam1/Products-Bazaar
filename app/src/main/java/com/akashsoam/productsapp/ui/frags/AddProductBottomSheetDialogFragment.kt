package com.akashsoam.productsapp.ui.frags

import android.os.Bundle
import android.view.View
import com.akashsoam.productsapp.MainActivity
import com.akashsoam.productsapp.R
import com.akashsoam.productsapp.databinding.FragmentAddProductBottomSheetDialogBinding
import com.akashsoam.productsapp.viewmodels.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.fragment_add_product_bottom_sheet_dialog) {
    val binding: FragmentAddProductBottomSheetDialogBinding by lazy {
        FragmentAddProductBottomSheetDialogBinding.bind(requireView())
    }
    lateinit var viewModel: ProductViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        binding.apply {
            // Set up spinner
            // Set up button
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }
}
