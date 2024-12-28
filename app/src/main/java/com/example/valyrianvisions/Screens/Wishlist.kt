package com.example.valyrianvisions.Screens

import CartViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.valyrianvisions.CommonComps.ScreenWithTopBarAndBottomNav
import com.example.valyrianvisions.R
import com.example.valyrianvisions.ViewModels.WishListViewModel

@Composable
fun WishlistScreen(navController: NavController, cartViewModel: CartViewModel, wishListViewModel: WishListViewModel = viewModel()){
    val wishlist = wishListViewModel.Items

    ScreenWithTopBarAndBottomNav(navController = navController, cartViewModel = cartViewModel, wishListViewModel = wishListViewModel)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(text = "Wishlist",
                    style = MaterialTheme.typography.headlineMedium, modifier = Modifier,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center)
            }
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            if(wishlist.isEmpty()){
                // Display message when wishlist is empty
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your wishlist is empty!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
            }else{
                wishlist.forEach{item->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp))
                    {
                        Image(painter = painterResource(id = item.imageRes), contentDescription = "Wishlist Image",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f))
                        {
                            Text(text = item.name, style = MaterialTheme.typography.bodyLarge,color = MaterialTheme.colorScheme.onBackground)
                            Text(text = "$${item.price}",style = MaterialTheme.typography.bodyMedium,color = MaterialTheme.colorScheme.onBackground,)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button( colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                            onClick = {wishListViewModel.removeWishlistItem(item)},
                        )
                        {
                            Text(text = stringResource(R.string.removeBtn))

                        }

                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            Row( modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly)
            {
                Button(onClick = { wishListViewModel.clearWish() }, modifier = Modifier.width(150.dp)) {
                    Text(text = stringResource(R.string.clearBtn))
                }

                Button(onClick = { /* Implement Buy Functionality */ },modifier = Modifier.width(150.dp)) {
                    Text("Add to Cart")
                }
            }



        }

    }
}