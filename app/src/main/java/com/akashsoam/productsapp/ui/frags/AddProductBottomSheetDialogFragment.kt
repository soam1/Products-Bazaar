package com.akashsoam.productsapp.ui.frags

import com.akashsoam.productsapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddProductBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.fragment_add_product_bottom_sheet_dialog) {

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }
}
