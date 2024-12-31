package com.example.valyrianvisions.CommonComps.ProdcutCards

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
import com.example.valyrianvisions.ViewModels.FeaturedProductViewModel
import com.example.valyrianvisions.model.FeaturedProduct

@Composable
fun FeaturedProductCard(product: FeaturedProduct, navController: NavController){
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .width(200.dp)
            .clickable { navController.navigate("detailedProductView/${product.image}") }
            .height(280.dp)
    )
    {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(130.dp).width(200.dp)
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
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
            Button(
                onClick = {navController.navigate("detailedProductView/${product.image}")},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Buy")
            }
        }

    }
}

@Composable
fun FeaturedRow(navController: NavController, viewModel: FeaturedProductViewModel){
    val featuredProducts by viewModel.featuredProduct.collectAsState()
    println("Rendering FeaturedRow with products: $featuredProducts")

    LazyRow(modifier = Modifier)
    {
        items(featuredProducts){
            product-> FeaturedProductCard(product = product, navController = navController)
        }
    }
}