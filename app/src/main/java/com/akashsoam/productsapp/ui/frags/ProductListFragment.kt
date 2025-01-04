package com.akashsoam.productsapp.ui.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akashsoam.productsapp.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
