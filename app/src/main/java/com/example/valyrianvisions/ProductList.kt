package com.example.valyrianvisions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.valyrianvisions.ViewModels.ProductViewModel
import com.example.valyrianvisions.model.Product

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .width(200.dp)
            .height(280.dp)
    ) {
        Column {
            AsyncImage(
                model = "http://10.0.2.2:8000/storage/${product.image_path}",
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
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Buy")
            }
        }
    }
}


@Composable
fun ProductList(productViewModel: ProductViewModel){
    val products by productViewModel.products.collectAsState()
    val error by productViewModel.error.collectAsState()

    if(error != null){
        Text(text = "Error: $error")
    }else if (products.isEmpty()){
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }else{
        LazyRow(modifier = Modifier) {
            items(products){
                product->
                ProductCard(product=product)
            }
        }
    }
}