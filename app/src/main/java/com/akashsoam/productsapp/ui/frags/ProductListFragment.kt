import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akashsoam.productsapp.data.adapters.ProductAdapter
import com.akashsoam.productsapp.databinding.FragmentProductListBinding
import com.akashsoam.productsapp.ui.frags.AddProductBottomSheetDialogFragment
import com.akashsoam.productsapp.ui.frags.ProductViewModel

class ProductListFragment : Fragment() {
    private lateinit var viewModel: ProductViewModel
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        setupRecyclerView()
        observeProducts()
        binding.searchButton.setOnClickListener { viewModel.searchProducts(binding.searchInput.text.toString()) }
        binding.addButton.setOnClickListener { showAddProductDialog() }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = ProductAdapter()
    }


    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner, { products ->
            (binding.recyclerView.adapter as ProductAdapter).submitList(products)
        })
    }

    private fun showAddProductDialog() {
        AddProductBottomSheetDialogFragment().show(childFragmentManager, "AddProductDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
