package com.akashsoam.productsapp.ui.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.akashsoam.productsapp.data.models.Product
import com.akashsoam.productsapp.databinding.FragmentAddProductBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddProductBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener { submitProduct() }
    }

    private fun submitProduct() {
        val product = Product(
            id = 0,
            productName = binding.productName.text.toString(),
            productType = binding.productType.text.toString(),
            price = binding.productPrice.text.toString().toDouble(),
            tax = binding.productTax.text.toString().toDouble(),
            imageUrl = binding.productImage.text.toString()
        )
        viewModel.addProduct(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
