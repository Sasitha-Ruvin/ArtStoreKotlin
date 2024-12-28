package com.example.valyrianvisions.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valyrianvisions.data.LatestProduct
import com.example.valyrianvisions.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LatestProductsViewModel: ViewModel() {
    private val _latestProducts = MutableStateFlow<List<LatestProduct>>(emptyList())
    val latestProducts: StateFlow<List<LatestProduct>> = _latestProducts

    init{
        fetchLatestProducts()
    }

    private fun fetchLatestProducts(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getLatestProducts()
                _latestProducts.value = response
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}