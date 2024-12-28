package com.example.valyrianvisions.CommonComps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.valyrianvisions.ViewModels.ForYouProductsViewModel
import com.example.valyrianvisions.data.ForYouProduct

@Composable
fun ForYouProductCard(product: ForYouProduct, navController: NavController){
    Card(
        modifier = Modifier.padding(horizontal = 12.dp).width(200.dp).height(280.dp)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(200.dp).fillMaxWidth()
            )
            Text(
                text = product.name,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            Text(
                text = "$${product.price}",
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
    }
}

@Composable
fun ForYouProduct(viewModel: ForYouProductsViewModel, navController: NavController) {
    val forYouProducts by viewModel.forYouProducts.collectAsState()

    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(forYouProducts) { product ->
            ForYouProductCard(product = product, navController = navController)
        }
    }
}