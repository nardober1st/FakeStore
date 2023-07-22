package com.oechslerbernardo.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.oechslerbernardo.fakestore.databinding.ActivityMainBinding
import com.oechslerbernardo.fakestore.hilt.service.ProductsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productsService: ProductsService

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refreshData()
        setupListeners()
    }

    private fun refreshData() {
        lifecycleScope.launchWhenStarted {
            binding.productImageViewLoadingProgressBar.isVisible = true
            val response = productsService.getAllProducts()
            binding.productImageView.load(
                data = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            ) {
                listener { request, result ->
                    binding.productImageViewLoadingProgressBar.isGone = true
                }
            }
            Log.i("DATA", response.body()!!.toString())
        }
    }

    private fun setupListeners() {
        // If its visible, when we click, it becomes invisible and vice versa
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
    }
}