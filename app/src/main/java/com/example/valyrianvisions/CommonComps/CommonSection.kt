package com.example.valyrianvisions.CommonComps

import CartViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.valyrianvisions.Navbar.BottomNav
import com.example.valyrianvisions.TopBar.TopBar
import com.example.valyrianvisions.ViewModels.WishListViewModel
import com.example.valyrianvisions.network.ConnectivityStatusBanner

@Composable
fun ScreenWithTopBarAndBottomNav(
    navController: NavController,
    showbackButton:Boolean = false,
    cartViewModel: CartViewModel,
    wishListViewModel: WishListViewModel,
    content: @Composable (Modifier) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        TopBarSection(navController = navController, cartViewModel, showbackButton)

        // Main Content
        Box(modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.background)) {

            content(Modifier.fillMaxSize())
        }

        // Bottom Navigation Bar
        BottomNavSection(navController = navController, wishListViewModel)
    }
}



@Composable
fun BottomNavSection(navController: NavController, wishListViewModel: WishListViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        BottomNav(navController = navController, modifier = Modifier, wishListViewModel = wishListViewModel )
    }
}


@Composable
fun TopBarSection(navController: NavController, cartViewModel: CartViewModel, showbackButton: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        TopBar(navController = navController, cartViewModel, showbackButton = showbackButton)
    }
}
