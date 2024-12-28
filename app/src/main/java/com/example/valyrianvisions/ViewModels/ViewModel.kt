package com.example.valyrianvisions.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.valyrianvisions.model.Pictures

class SharedViewModel: ViewModel() {
    var selectedPicture = mutableStateOf<Pictures?>(null)
        private set
    fun selectPicture(picture: Pictures) {
        selectedPicture.value = picture
    }
}