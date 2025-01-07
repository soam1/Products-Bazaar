package com.akashsoam.productsapp.ui.frags

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import com.airbnb.lottie.LottieAnimationView
import com.akashsoam.productsapp.MainActivity
import com.akashsoam.productsapp.R
import com.akashsoam.productsapp.databinding.FragmentAddProductBottomSheetDialogBinding
import com.akashsoam.productsapp.models.Product
import com.akashsoam.productsapp.viewmodels.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class AddProductBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.fragment_add_product_bottom_sheet_dialog) {

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 100
    }

    private lateinit var lottieAnimationView: LottieAnimationView

    private lateinit var binding: FragmentAddProductBottomSheetDialogBinding
    private lateinit var viewModel: ProductViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        binding = FragmentAddProductBottomSheetDialogBinding.bind(view)
        lottieAnimationView = binding.lottieAnimationView


        setupProductTypeSpinner()
        setupSubmitButton()
    }

    private fun setupProductTypeSpinner() {
        val productTypes =
            arrayOf("Utilities", "Clothing", "Food", "Books", "Health", "Beauty", "Gadgets")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.productTypeSpinner.adapter = adapter
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            val product = createProductFromInput()
            selectImageFromGallery(product)
        }
    }

    private fun createProductFromInput(): Product {
        return Product(
            product_name = binding.productName.text.toString(),
            product_type = binding.productTypeSpinner.selectedItem.toString(),
            price = binding.productPrice.text.toString().toDoubleOrNull() ?: 0.0,
            tax = binding.productTax.text.toString().toIntOrNull() ?: 0,
            image = ""
        )
    }

    private fun selectImageFromGallery(product: Product) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                viewModel.addProductWithImage(createProductFromInput(), it)

//                showSnackBar("Adding product...")
                showProductAddedImageView()
                //show lottie animation

            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProductAddedImageView() {
        // Hide any progress bars or other UI elements as needed
        hideProgressBar()

        // Set up and start the Lottie animation
        lottieAnimationView.visibility = View.VISIBLE
        lottieAnimationView.playAnimation()

        // Dismiss the dialog after the animation has completed
        lottieAnimationView.addAnimatorListener(object :
            AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                lottieAnimationView.visibility = View.GONE
                dismiss()  // Make sure this is the correct approach to dismissing in your app's flow
            }
        })

        // Optionally set a fixed delay if the animation does not have a set duration
        // This is just in case you want to ensure the animation plays for exactly 2 seconds
        lottieAnimationView.postDelayed({
            if (lottieAnimationView.isAnimating) {
                lottieAnimationView.cancelAnimation()
            }
            lottieAnimationView.visibility = View.GONE
            dismiss()
        }, 2000)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.getProductsList()
    }
}
