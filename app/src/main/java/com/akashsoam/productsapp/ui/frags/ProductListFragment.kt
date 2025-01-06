import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akashsoam.productsapp.MainActivity
import com.akashsoam.productsapp.R
import com.akashsoam.productsapp.databinding.FragmentProductListBinding
import com.akashsoam.productsapp.viewmodels.ProductViewModel

class ProductListFragment : Fragment(R.layout.fragment_product_list) {
    val binding: FragmentProductListBinding by lazy {
        FragmentProductListBinding.bind(requireView())
    }
    lateinit var viewModel: ProductViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        binding.apply {
            // Set up recycler view

            // Set up adapter
            // Set up observer

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
