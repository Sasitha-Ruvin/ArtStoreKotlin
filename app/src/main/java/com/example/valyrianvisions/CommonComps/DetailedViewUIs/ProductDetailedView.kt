package com.example.valyrianvisions.Screens

import CartViewModel
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.valyrianvisions.CommonComps.ScreenWithTopBarAndBottomNav
import com.example.valyrianvisions.ViewModels.WishListViewModel
import com.example.valyrianvisions.model.CartItem
import com.example.valyrianvisions.model.Pictures
import com.example.valyrianvisions.model.WishlistItem

@Composable
fun DetailedProductView(
    picture: Pictures,
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
    val name = stringResource(id = picture.stringResourceId)
    val price = picture.price


    ScreenWithTopBarAndBottomNav(navController = navController, showbackButton = true,cartViewModel, wishListViewModel) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = offsetX)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(vertical = 12.dp),
            ) {
                Spacer(modifier = Modifier.height(35.dp))

                // Product Image
                Image(
                    painter = painterResource(id = picture.imageResourceId),
                    contentDescription = stringResource(id = picture.stringResourceId),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = picture.stringResourceId),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp
                    )

                    TextButton(onClick = { val wishlistItem = WishlistItem(
                        imageRes = picture.imageResourceId,
                        name = name,
                        price = price
                    )
                        Toast.makeText(context, "Item added to Wishlist", Toast.LENGTH_SHORT).show()
                        wishListViewModel.addItemToWish(wishlistItem)
                    }) {
                        Icon(imageVector = Icons.Outlined.BookmarkBorder, contentDescription = "Wishlist")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "by",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "John Doe",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${price.format(2)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "captures the serene and mysterious beauty of the cosmos. The artwork portrays a vast expanse of deep, velvety darkness punctuated by countless twinkling stars.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { index ->
                        Icon(
                            imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star Rating",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    rating = index
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { if (quantity > 1) quantity-- }) {
                        Text(text = "-", fontSize = 24.sp)
                    }

                    Text(
                        text = "$quantity",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    TextButton(onClick = { quantity++ }) {
                        Text(text = "+", fontSize = 24.sp)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            val cartItem = CartItem(
                                imageRes = picture.imageResourceId,
                                name = name,
                                price = price,
                                quantity = quantity
                            )
                            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
                            cartViewModel.addItemToCart(cartItem)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 25.dp)
                    ) {
                        Text(text = "Add to Cart", style = MaterialTheme.typography.labelSmall)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
fun Double.format(digits: Int) = "%.${digits}f".format(this)
