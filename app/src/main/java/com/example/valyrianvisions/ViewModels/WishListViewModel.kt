package com.example.valyrianvisions.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.valyrianvisions.model.WishlistItem

class WishListViewModel(private val savedStateHandle: SavedStateHandle):ViewModel()
{
    private val _wishlistItem = mutableStateListOf<WishlistItem>()
    val Items : List<WishlistItem> get () = _wishlistItem
//Adding an item to wishlist
    fun addItemToWish(wishlistItem: WishlistItem){
        _wishlistItem.add(wishlistItem)
    }

    fun removeWishlistItem(wishlistItem: WishlistItem){
        _wishlistItem.remove(wishlistItem)
    }
//    Clearing all the items in the wishlist
    fun clearWish(){
        _wishlistItem.clear()
    }
}