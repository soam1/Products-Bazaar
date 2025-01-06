package com.akashsoam.productsapp.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akashsoam.productsapp.R
import com.akashsoam.productsapp.data.models.Product
import com.akashsoam.productsapp.databinding.ItemProductBinding
import com.bumptech.glide.Glide

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.productName
            binding.productPrice.text = product.price.toString()
            binding.productType.text = product.productType
            binding.productTax.text = "Tax: ${product.tax}%"
            if (product.imageUrl != null) {
                Glide.with(binding.productImage.context).load(product.imageUrl)
                    .placeholder(R.drawable.default_image).into(binding.productImage)
            } else {
                binding.productImage.setImageResource(R.drawable.default_image)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id // Assuming each product has a unique id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
