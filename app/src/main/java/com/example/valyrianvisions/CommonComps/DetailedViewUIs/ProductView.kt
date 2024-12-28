package com.example.valyrianvisions.CommonComps.DetailedViewUIs

import CartViewModel
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.valyrianvisions.CommonComps.ScreenWithTopBarAndBottomNav
import com.example.valyrianvisions.ProductItem
import com.example.valyrianvisions.ViewModels.WishListViewModel
import com.example.valyrianvisions.model.CartItem
import com.example.valyrianvisions.model.WishlistItem

@Composable
fun ProductView(
    product: ProductItem,
    navController: NavController,
    cartViewModel: CartViewModel,
    wishListViewModel: WishListViewModel,
    modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        startAnimation = true
    }
    val context = LocalContext.current

    val offsetX by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 3000.dp,
        animationSpec = tween(durationMillis = 400)
    )

    var quantity by remember { mutableStateOf(1) }
    var rating by remember { mutableStateOf(0) }
    var selectedColor by remember { mutableStateOf(Color.Gray) }
    val name = stringResource(id = product.stringResourceId)
    val price = product.price
    val desc = stringResource(id = product.descriptionResourceId)

    ScreenWithTopBarAndBottomNav(navController = navController, showbackButton = true, cartViewModel = cartViewModel, wishListViewModel) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = offsetX)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Image at the top
                Image(
                    painter = painterResource(id = product.imageResourceId),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Product name with favorite icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp
                    )
                    TextButton(onClick = { val wishlistItem = WishlistItem(
                        imageRes = product.imageResourceId,
                        name = name,
                        price = price)
                        Toast.makeText(context, "Item added to Wishlist", Toast.LENGTH_SHORT).show()
                        wishListViewModel.addItemToWish(wishlistItem)
                    }) {
                        Icon(imageVector = Icons.Outlined.BookmarkBorder, contentDescription = "Favorite Icon")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Product description
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Select Frame Color",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 15.sp)
                // Color selection
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    val colors = listOf(Color.LightGray, Color(0xFFE8DEF8), Color(0xFF65558F), Color.Yellow, Color.Magenta) // Example colors
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable { selectedColor = color }
                                .border(
                                    width = 2.dp,
                                    color = if (selectedColor == color) Color.Black else Color.Transparent,
                                    shape = CircleShape
                                )
                                .padding(4.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "$${product.price}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp)
                Spacer(modifier = Modifier.height(10.dp))

                // Star rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { index ->
                        Icon(
                            imageVector = if (index <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star Rating",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { rating = index }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Quantity selection and Add to Cart button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//
                    Button(
                        onClick = {
                            val cartItem = CartItem(
                                imageRes = product.imageResourceId,
                                name = name,
                                price = price,
                                quantity = quantity
                            )
                            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
                            cartViewModel.addItemToCart(cartItem)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Add to Cart", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
