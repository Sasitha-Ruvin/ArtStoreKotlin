package com.example.valyrianvisions.Firebase

import com.example.valyrianvisions.model.FeaturedProduct
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

object FirebaseHelper {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("featured")
    private val storage = FirebaseStorage.getInstance()

    suspend fun getFeaturedProducts(): List<FeaturedProduct>{
        val snapshot = database.get().await()
        return snapshot.children.mapNotNull { it.getValue(FeaturedProduct::class.java) }
    }
}