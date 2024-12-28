package com.example.valyrianvisions.ViewModels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valyrianvisions.model.User
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = getApplication<Application>().getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    private val userProfileData = mutableStateOf(
        User(
            username = "johnD",
            password = "*********",
            email = "john@mail.com",
            contact = "123-456-7890"
        )
    )

    val userProfile = userProfileData

    // Profile image state
    var profileImageBitmap = mutableStateOf<Bitmap?>(null)

    init {
        // Load user data from SharedPreferences when ViewModel is created
        val username = getUsername()
        val password = getPassword()
        val email = getEmail()
        val contact = getContact()

        userProfileData.value = User(
            username = username,
            password = password,
            email = email,
            contact = contact
        )

        // Load profile image from SharedPreferences
        profileImageBitmap.value = loadProfileImage()
    }

    fun updateUserData(username: String, password: String, email: String, contact: String) {
        userProfile.value = User(username, password, email, contact)
        saveUserData(username, password, email, contact)
    }


    fun updateProfileImage(newImage: Bitmap) {
        viewModelScope.launch {
            profileImageBitmap.value = newImage

            // Save the profile image to SharedPreferences
            saveProfileImageToStorage(newImage)
        }
    }

    private fun saveUserData(username: String, password: String, email: String, contact: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putString("email", email)
        editor.putString("contact", contact)
        editor.apply()
    }

    private fun getUsername(): String = sharedPreferences.getString("username", "johnD") ?: "johnD"
    private fun getPassword(): String = sharedPreferences.getString("password", "*********") ?: "*********"
    private fun getEmail(): String = sharedPreferences.getString("email", "john@mail.com") ?: "john@mail.com"
    private fun getContact(): String = sharedPreferences.getString("contact", "123-456-7890") ?: "123-456-7890"

    private fun saveProfileImageToStorage(bitmap: Bitmap) {
        val filePath = saveBitmapToFile(bitmap)
        val editor = sharedPreferences.edit()
        editor.putString("profile_image_path", filePath)
        editor.apply()
    }

    private fun saveBitmapToFile(bitmap: Bitmap): String {
        val file = File(getApplication<Application>().filesDir, "profile_image.jpg")
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private fun loadProfileImage(): Bitmap? {
        val imagePath = sharedPreferences.getString("profile_image_path", null)
        return if (imagePath != null) {
            BitmapFactory.decodeFile(imagePath)
        } else {
            null
        }
    }
}

