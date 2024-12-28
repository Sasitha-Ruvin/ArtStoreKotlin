package com.example.valyrianvisions.Screens

import CartViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.valyrianvisions.Animations.LoadingCircle
import com.example.valyrianvisions.CommonComps.ProdcutCards.ProductsGrid
import com.example.valyrianvisions.CommonComps.ScreenWithTopBarAndBottomNav
import com.example.valyrianvisions.CommonComps.SearchBar
import com.example.valyrianvisions.ViewModels.WishListViewModel
import com.example.valyrianvisions.data.PaintingsSource
import kotlinx.coroutines.delay

@Composable
fun PaintingsScreen(navController: NavController, cartViewModel: CartViewModel, wishListViewModel: WishListViewModel) {
    val paintings = PaintingsSource().loadPaintings()
    var search by remember { mutableStateOf("") }
    var startAnimation by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    LaunchedEffect(Unit)
    {
        delay(1500)
        isLoading = false

    }

    if(isLoading){
        LoadingCircle()
    }else{
        ScreenWithTopBarAndBottomNav(navController = navController, showbackButton = true, cartViewModel, wishListViewModel = wishListViewModel) {
            AnimatedVisibility(
                visible = startAnimation,
                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)


                ) {
                    Row(modifier = Modifier.fillMaxWidth() .padding(start = 16.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start) {
                        Text(text = "Paintings",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    SearchBar(search = search, onSearchChange = { search = it }, modifier = Modifier)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProductsGrid(products = paintings, productType = "paintings", navController)
                }
            }
        }
    }
}