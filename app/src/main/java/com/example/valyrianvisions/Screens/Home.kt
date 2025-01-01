package com.example.valyrianvisions.Screens

import CartViewModel
import android.app.Application
import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.valyrianvisions.Animations.LoadingCircle
import com.example.valyrianvisions.Authentications.AuthState
import com.example.valyrianvisions.Authentications.AuthViewModel
import com.example.valyrianvisions.CommonComps.ForYouProduct
import com.example.valyrianvisions.CommonComps.LatestProductList
import com.example.valyrianvisions.CommonComps.ProdcutCards.FeaturedRow
import com.example.valyrianvisions.R
import com.example.valyrianvisions.CommonComps.ScreenWithTopBarAndBottomNav
import com.example.valyrianvisions.CommonComps.SearchBar
import com.example.valyrianvisions.CommonComps.ProdcutCards.SwipeableEventSlideshow
import com.example.valyrianvisions.CommonComps.SectionsText
import com.example.valyrianvisions.ProductList
import com.example.valyrianvisions.ViewModels.FeaturedProductViewModel
import com.example.valyrianvisions.ViewModels.ForYouProductsViewModel
import com.example.valyrianvisions.ViewModels.LatestProductsViewModel
import com.example.valyrianvisions.ViewModels.ProductViewModel
import com.example.valyrianvisions.ViewModels.WishListViewModel
import com.example.valyrianvisions.data.ArtistSource
import com.example.valyrianvisions.data.DataSource
import com.example.valyrianvisions.data.EventResource
import com.example.valyrianvisions.model.Artists
import com.example.valyrianvisions.model.Pictures
import com.example.valyrianvisions.ui.theme.ValyrianVisionsTheme
import kotlinx.coroutines.delay


//Home Screen
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    wishListViewModel: WishListViewModel
) {
    var search by remember { mutableStateOf("") }
    val authState = authViewModel.authstate.observeAsState()
    var startAnimation by remember { mutableStateOf(false) }
    val events = EventResource().loadEvents()
    var isLoading by remember { mutableStateOf(true) }

    val latestProductsViewModel = LatestProductsViewModel();
    val productViewModel  = ProductViewModel()
    val featuredViewModel = remember { FeaturedProductViewModel() }
    val forYouProductsViewModel = ForYouProductsViewModel(LocalContext.current.applicationContext as Application)

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    LaunchedEffect(Unit) {
        delay(1500)
        isLoading = false
    }

    val offsetX by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 3000.dp,
        animationSpec = tween(durationMillis = 400)
    )

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    if (isLoading) {
        LoadingCircle()
    } else {
        ValyrianVisionsTheme {
            Scaffold { innerPadding ->
                ScreenWithTopBarAndBottomNav(
                    navController = navController,
                    showbackButton = false,
                    cartViewModel = cartViewModel,
                    wishListViewModel = wishListViewModel
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .offset(x = offsetX)
                            .padding(innerPadding) // Applying innerPadding to respect Scaffold content insets
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(5.dp))

                            // Main content
                            Column(
                                modifier = Modifier
                                    .weight(2f)
                                    .verticalScroll(rememberScrollState())
                                    .fillMaxWidth()
                                    .padding(bottom = 60.dp)
                            ) {
                                SearchBar(search = search, onSearchChange = { search = it }, modifier = Modifier)
                                Spacer(modifier = Modifier.height(15.dp))
                                Slideshow()
                                Spacer(modifier = Modifier.height(16.dp))
                                SectionsText("For You", navigateTo = "products", navController )
                                Spacer(modifier = Modifier.height(10.dp))
                                ForYouProduct(viewModel = forYouProductsViewModel, navController = navController)
                                Spacer(modifier = Modifier.height(16.dp))
                                SectionsText("Featured", navigateTo = "products", navController)
//                                FeaturedRow(navController = navController, viewModel = featuredViewModel)
                                ProductList(productViewModel = productViewModel)


                                Spacer(modifier = Modifier.height(10.dp))
                                Spacer(modifier = Modifier.height(25.dp))
                                SectionsText("Latest", navigateTo = "products", navController)
                                Spacer(modifier = Modifier.height(10.dp))
                                LatestProductList(viewModel = latestProductsViewModel, navController= navController)
                                Spacer(modifier = Modifier.height(25.dp))
                                CategoriesText()
                                Spacer(modifier = Modifier.height(20.dp))
                                CategoryButtons(navController = navController)
                                Spacer(modifier = Modifier.height(25.dp))
                                ImageWithOverlay()
                                Spacer(modifier = Modifier.height(15.dp))
                                FeaturedArtistText()
                                Spacer(modifier = Modifier.height(15.dp))
                                FeaturedArtistList(artistList = ArtistSource().loadArtists())
                                Spacer(modifier = Modifier.height(20.dp))
                                NewsText()
                                Spacer(modifier = Modifier.height(10.dp))
                                SwipeableEventSlideshow(eventList = events)
                            }
                        }
                    }
                }
            }
        }
    }
}


//Main Image Overlay
@Composable
fun ImageWithOverlay() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.art1),
            contentDescription = "Art One",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        // Overlaying Text
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(R.string.immerse_in_art),
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White, fontWeight = FontWeight.Bold)
            )
        }
    }
}

////Featured Product section Text
//@Composable
//fun FeaturedText(navController: NavController) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = "Featured",
//            fontSize = 20.sp,
//            style = MaterialTheme.typography.bodyMedium.copy(
//                color = MaterialTheme.colorScheme.onBackground,
//                fontWeight = FontWeight.Bold
//            )
//        )
//        Spacer(modifier = Modifier.weight(1f))
//        TextButton(
//            onClick = { navController.navigate("products") },
//            modifier = Modifier.align(Alignment.CenterVertically)
//        ) {
//            Text(
//                text = "Show more",
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    color = Color.Gray,
//                    fontWeight = FontWeight.Bold
//                ),
//                fontSize = 15.sp
//            )
//        }
//    }
//}
//
//@Composable
//fun LatestText(navController: NavController){
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ){
//        Text(
//            text = "Latest",
//            fontSize = 20.sp,
//            style = MaterialTheme.typography.bodyMedium.copy(
//                color = MaterialTheme.colorScheme.onBackground,
//                fontWeight = FontWeight.Bold
//            )
//        )
//        Spacer(modifier = Modifier.weight(1f))
//        TextButton(
//            onClick = { navController.navigate("products") },
//            modifier = Modifier.align(Alignment.CenterVertically)
//        ) {
//            Text(
//                text = "Show more",
//                style = MaterialTheme.typography.bodyMedium.copy(
//                    color = Color.Gray,
//                    fontWeight = FontWeight.Bold
//                ),
//                fontSize = 15.sp
//            )
//        }
//    }
//
//}




//Featured Product Card
@Composable
fun FeaturedProductCard(picture:Pictures, modifier: Modifier = Modifier,navController: NavController){
    Card(modifier = Modifier
        .padding(horizontal = 12.dp)
        .width(200.dp)
        .clickable { navController.navigate("detailedProductView/${picture.imageResourceId}") }
        .height(280.dp)) {
        Column {
            Image(painter = painterResource(picture.imageResourceId), contentDescription = stringResource(
                R.string.product_image
            ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .width(200.dp)
            )
            Text(text = stringResource(id = picture.stringResourceId),
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Text(text = "$${picture.price}",
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                )
            Button(onClick = { navController.navigate("detailedProductView/${picture.imageResourceId}") },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Buy")
            }
        }

    }


}

//Featured Products List
@Composable
fun FeaturedProductList(pictureList: List<Pictures>, navController: NavController) {
    LazyRow(modifier = Modifier) {
        items(pictureList) { picture ->
            FeaturedProductCard(picture = picture, modifier = Modifier.padding(10.dp), navController = navController)
        }
    }
}



//Slideshow Composable
@Composable
fun Slideshow(slideInterval: Long = 5000) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val imageHeight = if (isPortrait) 200.dp else 350.dp

    val imageResources = listOf(
        R.drawable.slide1,
        R.drawable.slideshow2,
        R.drawable.slideshow3
    )

    var currentIndex by remember { mutableStateOf(0) }

    // Update the current index after every slideInterval duration
    LaunchedEffect(currentIndex) {
        while (true) {
            delay(slideInterval)
            currentIndex = (currentIndex + 1) % imageResources.size
        }
    }

    // Crossfade for smooth transitions
    Crossfade(targetState = currentIndex, modifier = Modifier.fillMaxWidth()) { index ->
        Image(
            painter = painterResource(id = imageResources[index]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
        )
    }
}

@Composable
fun FeaturedArtistText(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.featured_artists),
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
    }

}

@Composable
fun FeaturedArtistCard(artists: Artists, modifier: Modifier = Modifier){
    Card(modifier = Modifier
        .padding(horizontal = 12.dp)
        .width(100.dp)
        .height(100.dp)
        .clip(CircleShape)
    )
    {
        Column {
        Image(painter = painterResource(artists.imageResourceId), contentDescription = null,
            contentScale = ContentScale.Crop, modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .clip(
                    CircleShape
                ))
        }
    }
}


@Composable
fun FeaturedArtistList(artistList: List<Artists>){
    LazyRow(modifier = Modifier){
        items(artistList){artist->
            FeaturedArtistCard(artists = artist, modifier = Modifier.padding((10.dp)))
        }
    }
}

//Category Text
@Composable
fun CategoriesText(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.categories),
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

//Catergory Buttons
@Composable
fun CategoryButtons(navController: NavController) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Spacing between buttons
    ) {
        items(listOf("Paintings", "Sketches", "Sculptures")) { category ->
            Button(
                onClick = { navController.navigate(category.lowercase()) }, // Navigate to category-specific screens
                modifier = Modifier
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = category)
            }
        }
    }
}

@Composable
fun NewsText(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.new_events),
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


