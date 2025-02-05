package com.example.valyrianvisions.Screens

import CartViewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.valyrianvisions.Animations.LoadingCircle
import com.example.valyrianvisions.CommonComps.ScreenWithTopBarAndBottomNav
import com.example.valyrianvisions.CommonComps.SearchBar
import com.example.valyrianvisions.CommonComps.SectionsText
import com.example.valyrianvisions.ProductItem
import com.example.valyrianvisions.R
import com.example.valyrianvisions.ViewModels.WishListViewModel
import com.example.valyrianvisions.data.DataSource
import com.example.valyrianvisions.data.PaintingsSource
import com.example.valyrianvisions.data.SculptureSource
import com.example.valyrianvisions.data.SketchSource
import com.example.valyrianvisions.model.Paintings
import com.example.valyrianvisions.model.Pictures
import com.example.valyrianvisions.model.Sculptures
import com.example.valyrianvisions.model.Sketch
import com.example.valyrianvisions.network.ConnectivityStatusBanner
import kotlinx.coroutines.delay

@Composable
fun ProductsScreen(navController:NavController, cartViewModel: CartViewModel, wishListViewModel: WishListViewModel){
    var search by remember { mutableStateOf("") }
    var startAnimation by remember { mutableStateOf(false) }
    val pictures = DataSource().loadPictures()
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val offsetX by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 3000.dp,
        animationSpec = tween(durationMillis = 600)
    )
    LaunchedEffect(Unit)
    {
        delay(1500)
        isLoading = false

    }
    if(isLoading){
        LoadingCircle()
    }else{
        ScreenWithTopBarAndBottomNav(navController = navController, showbackButton = false,cartViewModel, wishListViewModel) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .offset(x = offsetX)
                .verticalScroll(rememberScrollState())
            ) {
                ConnectivityStatusBanner()
                ProductMainText()
                Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(12.dp))
                SearchBar(search = search, onSearchChange = { search = it }, modifier = Modifier)
                Spacer(modifier = Modifier.height(12.dp))
                TrendingText()
                Spacer(modifier = Modifier.height(12.dp))
                TrendingProductList(pictureList = DataSource().loadPictures(), navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
                SectionsText(title = "Paintings", navigateTo = "paintings", navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
                PaintingsList(paintingsList = PaintingsSource().loadPaintings(), navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
                SectionsText(title = "Sketches", navigateTo = "sketches", navController = navController )
                Spacer(modifier = Modifier.height(20.dp))
                SketchesList(sketchesList = SketchSource().loadKSketches(), navController = navController)
                Spacer(modifier = Modifier.height(20.dp))
                SectionsText(title = "Sculptures", navigateTo = "sculptures", navController = navController )
                Spacer(modifier = Modifier.height(20.dp))
                SculpturesList(sculpturesList = SculptureSource().loadSculptures(), navController = navController)

            }


        }

    }
}





@Composable
fun ProductMainText(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(10.dp),
        horizontalAlignment = Alignment.Start) 
    {
        Text(text = "Find Your Favourite Artworks and Artists",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground)
        
    }
}

@Composable
fun SectionText(navController: NavController){
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(text = "Paintings",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = { navController.navigate("paintings")},
            modifier = Modifier.align(Alignment.CenterVertically))
        {
            Text(
                text = "Show more",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 15.sp
            )

        }
    }
}


@Composable
fun TrendingText(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(10.dp),
        horizontalAlignment = Alignment.Start)
    {
        Text(text = "Trending",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold)

    }
}

//Featured Product Card
@Composable
fun TrendingProductCard(picture:Pictures, modifier: Modifier = Modifier,navController: NavController){
    Card(modifier = Modifier
        .padding(horizontal = 12.dp)
        .width(100.dp)
        .height(80.dp)
        .clickable { navController.navigate("detailedProductView/${picture.imageResourceId}") }
        ) {
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
        }
    }
}
@Composable
fun TrendingProductList(pictureList: List<Pictures>, navController: NavController) {
    LazyRow(modifier = Modifier) {
        items(pictureList) { picture ->
            TrendingProductCard(picture = picture, modifier = Modifier.padding(10.dp), navController = navController)
        }
    }
}



@Composable
fun SketchesCard(sketch: Sketch, modifier: Modifier = Modifier, navController: NavController)
{
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .width(200.dp)

            .clickable { navController.navigate("detailed_view/${sketch.imageResourceId}/sketches") }
    ) {
        Column {
            Image(
                painter = painterResource(sketch.imageResourceId),
                contentDescription = stringResource(sketch.descriptionResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .width(220.dp)
            )
            Text(
                text = stringResource(id = sketch.stringResourceId),
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Text(
                text = "${sketch.price}$",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }

}

@Composable
fun SculpturesCard(sculpture: Sculptures, modifier: Modifier = Modifier, navController: NavController){
    Card(modifier = modifier
        .padding(horizontal = 12.dp)
        .width(200.dp)

        .clickable { navController.navigate("detailed_view/${sculpture.imageResourceId}/sculptures") }
    )
    {
        Column {
            Image(painter = painterResource(sculpture.imageResourceId),
                contentDescription = stringResource(sculpture.stringResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .width(220.dp)
            )
            Text(text = stringResource(id = sculpture.stringResourceId),
                style = MaterialTheme.typography.labelMedium,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Text(text = "${sculpture.price}$",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }


    }
}
@Composable
fun PaintingsCard(painting: Paintings, modifier: Modifier = Modifier, navController: NavController) {
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .width(200.dp)

            .clickable { navController.navigate("detailed_view/${painting.imageResourceId}/paintings") }
    ) {
        Column {
            Image(
                painter = painterResource(painting.imageResourceId),
                contentDescription = stringResource(painting.descriptionResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .width(220.dp)
            )
            Text(
                text = stringResource(id = painting.stringResourceId),
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Text(
                text = "${painting.price}$",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun SketchesList(sketchesList: List<Sketch>, navController: NavController){
    val limitedSketchesList = sketchesList.take(5)
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedSketchesList){ sketch->
            SketchesCard(sketch = sketch, navController = navController, modifier = Modifier.padding(10.dp))
        }

    }
}
@Composable
fun PaintingsList(paintingsList: List<Paintings>, navController: NavController) {

    val limitedPaintingsList = paintingsList.take(5)

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(limitedPaintingsList) { painting ->
            PaintingsCard(painting = painting, modifier = Modifier.padding(10.dp), navController = navController)
        }
    }
}

@Composable
fun SculpturesList(sculpturesList: List<Sculptures>,navController: NavController){
    val limitedSculpturesList = sculpturesList.take(5)
    LazyRow (modifier = Modifier.fillMaxWidth())
    {
        items(limitedSculpturesList){
            sculpture -> SculpturesCard(sculpture = sculpture, navController = navController, modifier = Modifier.padding(10.dp))
        }

    }

}



//@Composable
//fun ProductGrid(pictures: List<Pictures>) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(pictures.size) { index ->
//            ProductCard(picture = pictures[index])
//        }
//    }
//}
//@Composable
//fun ProductCard(picture: Pictures) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { }
//            .padding(8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.padding(8.dp)
//        ) {
//            Image(
//                painter = painterResource(id = picture.imageResourceId),
//                contentDescription = stringResource(id = picture.stringResourceId),
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = stringResource(id = picture.stringResourceId),
//                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
//                color = Color.Black
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = "${picture.price}$",
//                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
//                color = Color.Black
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Button(
//                onClick = {},
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
//            ) {
//                Text(text = "Buy", color = Color.White)
//            }
//        }
//    }
//}
