package com.akashsoam.productsapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.akashsoam.productsapp.databinding.ActivityMainBinding
import com.akashsoam.productsapp.db.ProductDatabase
import com.akashsoam.productsapp.repository.ProductRepository
import com.akashsoam.productsapp.viewmodels.ProductViewModel
import com.akashsoam.productsapp.viewmodels.ProductViewModelProviderFactory
import com.akashsoam.productsapp.viewmodels.SplashViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: ProductViewModel

    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition() {
                // Keep the splash screen visible, till the viewModel is loading
                !splashViewModel.isLoading.value
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //now finally architecture implement karo
        val repository = ProductRepository(ProductDatabase(this))
        val viewModelProviderFactory = ProductViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ProductViewModel::class.java)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
//        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}