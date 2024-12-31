package com.example.valyrianvisions.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valyrianvisions.Firebase.FirebaseHelper
import com.example.valyrianvisions.Firebase.FirebaseRepository
import com.example.valyrianvisions.model.FeaturedProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeaturedProductViewModel : ViewModel() {
    private val _featureProducts = MutableStateFlow<List<FeaturedProduct>>(emptyList())
    val featuredProduct: StateFlow<List<FeaturedProduct>> = _featureProducts

    init {
        fetchFeaturedProducts()
    }

    private fun fetchFeaturedProducts() {
        viewModelScope.launch {
            try {
                val products = FirebaseRepository.getFeaturedProducts()
                _featureProducts.value = products
                println("Fetched products: $products") // Add logging
            } catch (e: Exception) {
                println("Error in fetchFeaturedProducts: ${e.message}") // Add logging
                e.printStackTrace()
            }
        }
    }
}
