package com.example.bizzcardapp.ui.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bizzcardapp.model.UserProfile
import com.example.bizzcardapp.ui.components.ImagePreview
import com.example.bizzcardapp.ui.navigation.Screen
import androidx.core.net.toUri

//@Composable
//fun ProfileScreen(
//    navController: NavHostController,
//    vm: ProfileViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val profile by vm.profile.collectAsState()
//
//    // Load saved profile once
//    LaunchedEffect(Unit) {
//        vm.load()
//    }
//
//    var name by remember { mutableStateOf(profile.name) }
//    var title by remember { mutableStateOf(profile.title) }
//    var email by remember { mutableStateOf(profile.email) }
//    var bio by remember { mutableStateOf(profile.bio) }
//    var imgUri by remember { mutableStateOf(profile.profileImageUri) }
//
//    // Image picker
//    val pickImage = rememberLauncherForActivityResult(
//        ActivityResultContracts.GetContent()
//    ) { uri ->
//        imgUri = uri?.toString()
//    }
//
//    BackHandler { navController.popBackStack() }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(bottom = 30.dp)
//    ) {
//        // Header
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp)
//                .background(
//                    Brush.linearGradient(
//                        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
//                    )
//                ),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("Create Your Profile", color = Color.White)
//        }
//
//        // Image
//        Box(
//            modifier = Modifier
//                .offset(y = (-60).dp)
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(130.dp)
//                    .background(Color.LightGray, CircleShape)
//                    .clickable { pickImage.launch("image/*") },
//                contentAlignment = Alignment.Center
//            ) {
//                if (imgUri != null) {
//                    ImagePreview(uri = imgUri!!.toUri(), size = 130.dp)
//                } else {
//                    Text("Tap to upload", color = Color.White)
//                }
//            }
//        }
//
//        // Fields
//        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
//            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") })
//            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title / Role") })
//            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
//            OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Short Bio") })
//        }
//
//        Button(
//            onClick = {
//                try {
//                    vm.updateProfile(
//                        UserProfile(
//                            name = name,
//                            title = title,
//                            email = email,
//                            bio = bio,
//                            profileImageUri = imgUri
//                        )
//                    )
//
//                    Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
//                    try {
//                        navController.navigate(Screen.Projects.route)
//                    } catch (e: Exception){
//                        e.message
//                    }
//
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Save & Next")
//        }
//
//    }
//}
@Composable
fun ProfileScreen(
    navController: NavHostController,
    vm: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val profile by vm.profile.collectAsState()

    LaunchedEffect(Unit) {
        vm.load()
    }

    var name by remember { mutableStateOf(profile.name) }
    var title by remember { mutableStateOf(profile.title) }
    var email by remember { mutableStateOf(profile.email) }
    var bio by remember { mutableStateOf(profile.bio) }
    var imgUri by remember { mutableStateOf(profile.profileImageUri) }

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imgUri = uri?.toString() }

    BackHandler { navController.popBackStack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 30.dp) // avoids tiny cut at the bottom
    ) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Create Your Profile", color = Color.White)
        }

        // Profile Image
        Box(
            modifier = Modifier
                .offset(y = (-60).dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(Color.LightGray, CircleShape)
                    .clickable { pickImage.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imgUri != null)
                    ImagePreview(uri = imgUri!!.toUri(), size = 130.dp)
                else
                    Text("Tap to upload", color = Color.White)
            }
        }

        // Centered form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title / Role") },
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Short Bio") },
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    try {
                        vm.updateProfile(
                            UserProfile(
                                name = name,
                                title = title,
                                email = email,
                                bio = bio,
                                profileImageUri = imgUri
                            )
                        )
                        Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.Projects.route)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Save & Next")
            }
        }
    }
}
