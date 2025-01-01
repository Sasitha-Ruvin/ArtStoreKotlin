package com.example.valyrianvisions.Firebase

import com.example.valyrianvisions.model.FeaturedProduct
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseRepository {
    suspend fun getFeaturedProducts(): List<FeaturedProduct> {

        val customDatabaseURL = "https://valyrian-visions-c7129.asia-southeast1.firebasedatabase.app/"
        val database = Firebase.database(customDatabaseURL)
        val ref = database.getReference("featured")

        return try {
            val snapshot = ref.get().await()
            println("Raw snapshot: ${snapshot.value}")
            snapshot.children.mapNotNull { it.getValue(FeaturedProduct::class.java) }
        } catch (e: Exception) {

            println("Error fetching featured products: ${e.message}")
            emptyList()
        }
    }
}
