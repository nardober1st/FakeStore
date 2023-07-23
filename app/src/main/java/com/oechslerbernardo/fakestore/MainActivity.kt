package com.oechslerbernardo.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.oechslerbernardo.fakestore.databinding.ActivityMainBinding
import com.oechslerbernardo.fakestore.hilt.service.ProductsService
import com.oechslerbernardo.fakestore.model.network.NetworkProduct
import com.oechslerbernardo.fakestore.model.domain.Product
import com.oechslerbernardo.fakestore.model.mapper.ProductMapper
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productsService: ProductsService

    @Inject
    lateinit var productMapper: ProductMapper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        lifecycleScope.launchWhenStarted {

            val response: Response<List<NetworkProduct>> = productsService.getAllProducts()
            val domainProducts: List<Product> = response.body()?.map { // this map fun will give us each element in 'response.body()'
                productMapper.buildFrom(networkProduct = it)
            } ?: emptyList()
            controller.setData(domainProducts)

            if (domainProducts.isEmpty()) {
                Snackbar.make(binding.root, "Failed to fetch", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        // If its visible, when we click, it becomes invisible and vice versa
        /*binding.cardView.setOnClickListener {
        binding.cardView.setOnClickListener {
            binding.productDescriptionTextView.apply {
                isVisible = !isVisible
            }
        }

        // If its visible, when we click, it becomes invisible and vice versa
        binding.addToCartButton.setOnClickListener {
            binding.inCartView.apply {
                isVisible = !isVisible
            }
        }

        // check if its favorite or not and display the corresponding drawable on the UI
        var isFavorite = false
        binding.favoriteImageView.setOnClickListener {
            val imageRes = if (isFavorite) {
                R.drawable.ic_round_favorite_border_24
            } else {
                R.drawable.ic_round_favorite_24
            }
            binding.favoriteImageView.setIconResource(imageRes)
            isFavorite = !isFavorite
        }
        }*/
    }
}
