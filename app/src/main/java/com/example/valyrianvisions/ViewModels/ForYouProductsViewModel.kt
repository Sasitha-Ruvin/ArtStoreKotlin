package com.example.valyrianvisions.ViewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.valyrianvisions.data.ForYouProduct
import com.example.valyrianvisions.network.RetrofitInstanceForYou
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class ForYouProductsViewModel(application: Application): AndroidViewModel(application) {
    private val _forYouProducts = MutableStateFlow<List<ForYouProduct>>(emptyList())
    val forYouProducts: StateFlow<List<ForYouProduct>> = _forYouProducts

    init{
        fetchForYouProducts()
    }

    private fun fetchForYouProducts(){
        viewModelScope.launch {
            try {
                if(isOnline()){
                    val response = RetrofitInstanceForYou.api.getForYouProducts()
                    _forYouProducts.value = response
                }else{
                    val localData = loadLoaclData()
                    _forYouProducts.value = localData
                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun isOnline(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return  networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport((NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    private fun loadLoaclData(): List<ForYouProduct>{
        val assetManager = getApplication<Application>().assets
        val inputStream = assetManager.open("for_you_local.json")
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<ForYouProduct>>() {}.type
        return Gson().fromJson(reader, type)
    }
}