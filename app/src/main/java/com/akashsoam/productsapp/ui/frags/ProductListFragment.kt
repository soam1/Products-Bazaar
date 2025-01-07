import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akashsoam.productsapp.MainActivity
import com.akashsoam.productsapp.R
import com.akashsoam.productsapp.adapters.ProductAdapter
import com.akashsoam.productsapp.databinding.FragmentProductListBinding
import com.akashsoam.productsapp.models.Product
import com.akashsoam.productsapp.util.Resource
import com.akashsoam.productsapp.viewmodels.ProductViewModel

class ProductListFragment : Fragment(R.layout.fragment_product_list) {
    val binding: FragmentProductListBinding by lazy {
        FragmentProductListBinding.bind(requireView())
    }
    lateinit var viewModel: ProductViewModel
    lateinit var productAdapter: ProductAdapter
    lateinit var searchView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        searchView = binding.searchView
        binding.apply {
            // Set up recycler view
            // Set up adapter
            productAdapter = ProductAdapter()
            recyclerView.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
            }
            // Set up observer
            viewModel.productsList.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { productResponse ->
                            productAdapter.differ.submitList(productResponse as List<Product>)
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Log.e("ProductListFragment", "This error occured: $message")
                        }
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }

            })



            addButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_productListFragment_to_addProductBottomSheetDialogFragment
                )
            }

        }

        setupSearchView()


    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    //todo: Implement search functionality properly
    private fun setupSearchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let { viewModel.filterProducts(it) }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let { viewModel.filterProducts(it) }
//                return true
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
