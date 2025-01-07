package com.akashsoam.productsapp.ui.frags

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.akashsoam.productsapp.MainActivity
import com.akashsoam.productsapp.R
import com.akashsoam.productsapp.databinding.FragmentAddProductBottomSheetDialogBinding
import com.akashsoam.productsapp.models.Product
import com.akashsoam.productsapp.util.Resource
import com.akashsoam.productsapp.viewmodels.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

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
            val productTypes =
                arrayOf("Utilities", "Clothing", "Food", "Books", "Health", "Beauty", "Gadgets")
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productTypes)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            productTypeSpinner.adapter = adapter

            submitButton.setOnClickListener {
                val productName = binding.productName.text.toString()
                val productType = productTypeSpinner.selectedItem.toString()
                val productPrice = binding.productPrice.text.toString().toDoubleOrNull() ?: 0.0
                val productTax = binding.productTax.text.toString().toIntOrNull() ?: 0
                val productImage = binding.productImage.text.toString()

                val product = Product(
                    product_name = productName,
                    product_type = productType,
                    price = productPrice,
                    tax = productTax,
                    image = productImage
                )
                viewModel.addProduct(product)

                //set up observer
                viewModel.productAdditionResult.observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
//                            showSnackBar("Product added successfully")
                            showProductAddedImageView()
                            response.data?.let { productResponse ->
                                Log.d(
                                    "AddProductBottomSheetDialogFragment",
                                    "Product added successfully"
                                )
                            }

                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Log.e(
                                    "AddProductBottomSheetDialogFragment",
                                    "This error occured: $message"
                                )
                            }
                        }

                        is Resource.Loading -> {
                            showProgressBar()
                            Log.d("AddProductBottomSheetDialogFragment", "Loading...")
                        }
                    }
                }

            }
        }
    }

    private fun showProductAddedImageView() {
        binding.productAddedImageView.visibility = View.VISIBLE
        //make it disappear after 2 seconds
        binding.productAddedImageView.postDelayed({
            binding.productAddedImageView.visibility = View.GONE
        }, 3000)
        dismiss()
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}