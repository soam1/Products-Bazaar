package com.akashsoam.productsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akashsoam.productsapp.R
import com.akashsoam.productsapp.databinding.ItemProductBinding
import com.akashsoam.productsapp.models.Product
import com.bumptech.glide.Glide

//class ProductAdapter(private val products: List<Product>) :
//class ProductAdapter(private val products: MutableLiveData<Resource<ProductResponse>>) :
class ProductAdapter() :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            productName.text = product.product_name
            productType.text = "category:${product.product_type}"
            productPrice.text = "Price: ${product.price}"
            productTax.text = "Tax: ${product.tax}"
            if (product.image.isNotEmpty()) {
                Glide.with(root.context).load(product.image).into(productImage)
            } else {
                productImage.setImageResource(R.drawable.default_image1)
            }

//            setOnClickListener {
//                onItemClickListener?.let { click ->
//                    click(product)
//                }
        }
    }

    override fun getItemCount(): Int {
//        return products.size
        return differ.currentList.size

    }

    //    DiffUtilCallBack implementation
    private val diffUtilCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtilCallBack)

    //on item touch listener for future use
//    private var onItemClickListener: ((Product) -> Unit)? = null
//    fun setOnItemClickListener(listener: (Product) -> Unit) {
//        onItemClickListener = listener
//    }
}