package com.example.valyrianvisions.CommonComps

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.valyrianvisions.ViewModels.LatestProductsViewModel
import com.example.valyrianvisions.data.LatestProduct

@Composable
fun LatestProductCard(product:LatestProduct, navController: NavController){
    Log.d("LatestProductCard", "Loading product: ${product.name}, Image URL: ${product.imageUrl}")
    Card(
        modifier = Modifier.padding(horizontal = 12.dp)
            .width(200.dp)
            .height(280.dp)
    ){
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .width(200.dp)
            )
            Text(
                text = product.name,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            Text(
                text = "$${product.price}",
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {"To DO"}
            ) {
                Text(text = "Buy")
            }
        }
    }
}

@Composable
fun LatestProductList(viewModel: LatestProductsViewModel, navController: NavController){
    val latestProducts by viewModel.latestProducts.collectAsState()

    LazyRow(modifier = Modifier){
        items(latestProducts){
            product-> LatestProductCard(product= product, navController = navController)
        }
    }
}