package com.example.valyrianvisions.Screens

import CartViewModel
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.valyrianvisions.Animations.LoadingCircle
import com.example.valyrianvisions.Authentications.AuthState
import com.example.valyrianvisions.Authentications.AuthViewModel
import com.example.valyrianvisions.CommonComps.ScreenWithTopBarAndBottomNav
import com.example.valyrianvisions.R
import com.example.valyrianvisions.ViewModels.UserProfileViewModel
import com.example.valyrianvisions.ViewModels.WishListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun UserProfile(
    navController: NavController,
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    wishListViewModel: WishListViewModel,
    userProfileViewModel: UserProfileViewModel
) {
    var isEditMode by remember { mutableStateOf(false) }
    val userProfile = userProfileViewModel.userProfile.value
    val authState by authViewModel.authstate.observeAsState()
    var isLoading by remember { mutableStateOf(true) }
    var startAnimation by remember { mutableStateOf(false) }

    // Profile image bitmap from ViewModel
    val profileImageBitmap by userProfileViewModel.profileImageBitmap

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // State to control if dialog is shown
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate("login") {
                popUpTo("profile") { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(1500)
        isLoading = false
    }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val offsetX by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 3000.dp,
        animationSpec = tween(durationMillis = 400)
    )

    // Launchers for image capture and image selection
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val bitmap = uriToBitmap(uri, context.contentResolver)
                if (bitmap != null) {
                    userProfileViewModel.updateProfileImage(bitmap)
                }
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap: Bitmap? ->
            if (bitmap != null) {
                userProfileViewModel.updateProfileImage(bitmap)
            }
        }
    )

    if (isLoading) {
        LoadingCircle()
    } else {
        ScreenWithTopBarAndBottomNav(navController = navController,
            showbackButton = false,
            cartViewModel = cartViewModel,
            wishListViewModel = wishListViewModel
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            if (isEditMode) {
                                coroutineScope.launch {
                                    userProfileViewModel.updateUserData(
                                        userProfile.username,
                                        userProfile.password,
                                        userProfile.email,
                                        userProfile.contact
                                    )
                                }
                            }
                            isEditMode = !isEditMode
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = if (isEditMode) Icons.Outlined.Check else Icons.Outlined.Edit,
                            contentDescription = if (isEditMode) "Save" else "Edit"
                        )
                    }
                },
                content = { scaffoldPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(scaffoldPadding)
                            .verticalScroll(rememberScrollState())
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // Profile Image Section
                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            if (profileImageBitmap != null) {
                                Image(
                                    bitmap = profileImageBitmap!!.asImageBitmap(),
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.artist2),
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            // Pencil Icon to upload image or take a photo
                            FloatingActionButton(
                                onClick = {
                                    showDialog = true // Show dialog when clicked
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit Image"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // User Info
                        Text(
                            text = userProfile.username,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Username Field
                            UserInfoField(
                                label = "Username",
                                value = userProfile.username,
                                isEditMode = isEditMode
                            ) { newValue ->
                                userProfile.username = newValue
                            }

                            // Email Field
                            UserInfoField(
                                label = "Email",
                                value = userProfile.email,
                                isEditMode = isEditMode
                            ) { newValue ->
                                userProfile.email = newValue
                            }

                            // Contact Field
                            UserInfoField(
                                label = "Contact",
                                value = userProfile.contact,
                                isEditMode = isEditMode
                            ) { newValue ->
                                userProfile.contact = newValue
                            }

                            // Password Field
                            UserInfoField(
                                label = "Password",
                                value = userProfile.password,
                                isEditMode = isEditMode
                            ) { newValue ->
                                userProfile.password = newValue
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { authViewModel.signOut() },
                                modifier = Modifier.weight(0.5f).width(120.dp),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                            ) {
                                Text(text = "Logout")
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.weight(0.5f).width(120.dp),
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                            ) {
                                Text(text = "Delete")
                            }
                        }
                    }
                }
            )
        }
    }

    // Show the dialog if state is true
    if (showDialog) {
        showImageSelectionDialog(
            onUploadClicked = {
                galleryLauncher.launch("image/*")
                showDialog = false
            },
            onTakePhotoClicked = {
                cameraLauncher.launch(null)
                showDialog = false
            },
            onDismiss = { showDialog = false } // Close dialog
        )
    }
}

// Utility function to show dialog for image source selection
@Composable
fun showImageSelectionDialog(
    onUploadClicked: () -> Unit,
    onTakePhotoClicked: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onUploadClicked) {
                    Text("Upload Image")
                }
                Button(onClick = onTakePhotoClicked) {
                    Text("Take a Photo")
                }
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


// Utility function to convert Uri to Bitmap
fun uriToBitmap(uri: Uri, contentResolver: ContentResolver): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }
    } catch (e: IOException) {
        Log.e("UserProfile", "Error decoding image", e)
        null
    }
}


@Composable
fun UserInfoField(label: String, value: String, isEditMode: Boolean, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        if (isEditMode) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onValueChange(it)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onValueChange(text) }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            )
        } else {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}