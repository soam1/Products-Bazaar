import androidx.fragment.app.Fragment
import com.akashsoam.productsapp.R

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }
}
