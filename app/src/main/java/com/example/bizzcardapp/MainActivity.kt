package com.example.bizzcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bizzcardapp.ui.theme.PortfolioTheme
import com.example.bizzcardapp.ui.portfolio.PortfolioScreen
import com.example.bizzcardapp.ui.profile.ProfileScreen
import com.example.bizzcardapp.ui.projects.ProjectsScreen
import com.example.bizzcardapp.ui.navigation.Screen


//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.ui.res.painterResource
//import androidx.core.graphics.scale
//import androidx.core.net.toUri
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.graphics.pdf.PdfDocument
//import android.net.Uri
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.BackHandler
//import androidx.activity.compose.setContent
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.material3.CardDefaults.cardElevation
//import androidx.compose.material3.MaterialTheme.colorScheme
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.content.FileProvider
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import coil.compose.rememberAsyncImagePainter
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.launch
//import java.io.File
//import java.io.FileOutputStream
//import java.io.InputStream
//import java.util.UUID
//
//// ---------- Data classes ----------
//data class UserProfile(
//    val name: String = "",
//    val title: String = "",
//    val email: String = "",
//    val bio: String = "",
//    val profileImageUri: String? = null // saved as String
//)
//
//data class PortfolioItem(
//    val id: String = UUID.randomUUID().toString(),
//    val title: String,
//    val description: String,
//    val link: String? = null,
//    val imageUri: String? = null
//)
//
//// ---------- DataStore setup ----------
//private const val PREFS_NAME = "portfolio_prefs"
//private val Context.dataStore by preferencesDataStore(name = PREFS_NAME)
//private val KEY_PROFILE = stringPreferencesKey("user_profile_json")
//private val KEY_PROJECTS = stringPreferencesKey("projects_json")
//
//suspend fun saveProfile(context: Context, profile: UserProfile) {
//    val gson = Gson()
//    val json = gson.toJson(profile)
//    context.dataStore.edit { prefs ->
//        prefs[KEY_PROFILE] = json
//    }
//}
//
//suspend fun loadProfile(context: Context): UserProfile {
//    val gson = Gson()
//    val prefs = context.dataStore.data.first()
//    val json = prefs[KEY_PROFILE] ?: return UserProfile()
//    return try {
//        gson.fromJson(json, UserProfile::class.java)
//    } catch (_: Exception) {
//        UserProfile()
//    }
//}
//
//suspend fun saveProjects(context: Context, projects: List<PortfolioItem>) {
//    val gson = Gson()
//    val json = gson.toJson(projects)
//    context.dataStore.edit { prefs ->
//        prefs[KEY_PROJECTS] = json
//    }
//}
//
//suspend fun loadProjects(context: Context): List<PortfolioItem> {
//    val gson = Gson()
//    val prefs = context.dataStore.data.first()
//    val json = prefs[KEY_PROJECTS] ?: return emptyList()
//    return try {
//        val type = object : TypeToken<List<PortfolioItem>>() {}.type
//        gson.fromJson<List<PortfolioItem>>(json, type) ?: emptyList()
//    } catch (_: Exception) {
//        emptyList()
//    }
//}
//
//// ---------- Navigation routes ----------
//sealed class Screen(val route: String) {
//    object Profile : Screen("profile")
//    object Projects : Screen("projects")
//    object Portfolio : Screen("portfolio")
//}
//
//// ---------- MainActivity ----------
//class MainActivity : ComponentActivity() {
//    @OptIn(ExperimentalMaterial3Api::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            PortfolioTheme {
//                val navController = rememberNavController()
//                val context = LocalContext.current
//
//                val profileState = remember { mutableStateOf(UserProfile()) }
//                val projectsState = remember { mutableStateListOf<PortfolioItem>() }
//                val scope = rememberCoroutineScope()
//
//                // load saved profile & projects
//                LaunchedEffect(Unit) {
//                    val p = loadProfile(context)
//                    profileState.value = p
//
//                    val projects = loadProjects(context)
//                    projectsState.clear()
//                    projectsState.addAll(projects)
//                }
//
//                Scaffold(
//                    topBar = {
//                        TopAppBar(
//                            title = {
//                                Text(
//                                    "Portfolio Builder",
//                                    style = MaterialTheme.typography.titleLarge,
//                                    color = Color.White
//                                )
//                            },
//                            colors = TopAppBarDefaults.topAppBarColors(
//                                containerColor = colorScheme.primary
//                            )
//                        )
//                    }
//                ) { innerPadding ->
//                    NavHost(
//                        navController = navController,
//                        startDestination = Screen.Profile.route,
//                        modifier = Modifier.padding(innerPadding)
//                    ) {
//                        composable(Screen.Profile.route) {
//                            ProfileScreen(
//                                navController = navController,
//                                profile = profileState.value,
//                                onProfileSaved = { updated ->
//                                    profileState.value = updated
//                                    scope.launch { saveProfile(context, updated) }
//                                }
//                            )
//                        }
//
//                        composable(Screen.Projects.route) {
//                            ProjectsScreen(
//                                navController = navController,
//                                projects = projectsState,
//                                onProjectsUpdated = { updated ->
//                                    projectsState.clear()
//                                    projectsState.addAll(updated)
//                                    scope.launch { saveProjects(context, updated) }
//                                },
//                                onDone = {
//                                    navController.navigate(Screen.Portfolio.route)
//                                }
//                            )
//                        }
//
//                        composable(Screen.Portfolio.route) {
//                            PortfolioScreen(
//                                navController = navController,
//                                profile = profileState.value,
//                                projects = projectsState.toList(),
//                                onEdit = { navController.navigate(Screen.Profile.route) }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// ---------- Profile Screen ----------
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileScreen(
//    navController: NavHostController,
//    profile: UserProfile,
//    onProfileSaved: (UserProfile) -> Unit
//) {
//    val context = LocalContext.current
//
//    val nameState = rememberSaveable { mutableStateOf(profile.name) }
//    val titleState = rememberSaveable { mutableStateOf(profile.title) }
//    val emailState = rememberSaveable { mutableStateOf(profile.email) }
//    val bioState = rememberSaveable { mutableStateOf(profile.bio) }
//    val imageUriState = rememberSaveable { mutableStateOf(profile.profileImageUri) }
//
//    val pickImageLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            imageUriState.value = uri?.toString()
//        }
//
//    var showExitDialog by remember { mutableStateOf(false) }
//
//    BackHandler {
//        if (navController.previousBackStackEntry != null) {
//            navController.popBackStack()
//        } else {
//            showExitDialog = true
//        }
//    }
//
//    if (showExitDialog) {
//        AlertDialog(
//            onDismissRequest = { showExitDialog = false },
//            confirmButton = {
//                TextButton(onClick = { (context as? ComponentActivity)?.finish() }) {
//                    Text("Exit")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showExitDialog = false }) {
//                    Text("Cancel")
//                }
//            },
//            title = { Text("Exit app?") },
//            text = { Text("Are you sure you want to exit the app?") }
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        // Gradient header
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp)
//                .background(
//                    Brush.linearGradient(
//                        listOf(
//                            colorScheme.primary,
//                            colorScheme.secondary
//                        )
//                    )
//                ),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                "Create Your Profile",
//                color = Color.White,
//                style = MaterialTheme.typography.headlineMedium
//            )
//        }
//
//        // Floating profile image
//        Box(
//            modifier = Modifier
//                .offset(y = (-60).dp)
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(130.dp)
//                    .clip(CircleShape)
//                    .background(Color.LightGray)
//                    .border(4.dp, Color.White, CircleShape)
//                    .clickable { pickImageLauncher.launch("image/*") },
//                contentAlignment = Alignment.Center
//            ) {
//                if (imageUriState.value != null) {
//                    ImagePreview(Uri.parse(imageUriState.value), 130.dp)
//                } else {
//                    Text("Tap to upload", color = Color.White)
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Card with text fields
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            shape = MaterialTheme.shapes.large,
//            elevation = cardElevation(5.dp)
//        ) {
//            Column(modifier = Modifier.padding(20.dp)) {
//                CustomInput("Full Name", nameState.value) { nameState.value = it }
//                CustomInput("Title / Role", titleState.value) { titleState.value = it }
//                CustomInput("Email", emailState.value) { emailState.value = it }
//                CustomInput("Short Bio", bioState.value, maxLines = 4) { bioState.value = it }
//            }
//        }
//
//        Button(
//            onClick = {
//                val updated = profile.copy(
//                    name = nameState.value,
//                    title = titleState.value,
//                    email = emailState.value,
//                    bio = bioState.value,
//                    profileImageUri = imageUriState.value
//                )
//                onProfileSaved(updated)
//                Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
//                navController.navigate(Screen.Projects.route)
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            Text("Save & Next")
//        }
//    }
//}
//
//@Composable
//fun CustomInput(
//    label: String,
//    value: String,
//    maxLines: Int = 1,
//    onChange: (String) -> Unit
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onChange,
//        label = { Text(label) },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 6.dp),
//        shape = MaterialTheme.shapes.medium,
//        maxLines = maxLines
//    )
//}
//
//// ---------- Projects Screen ----------
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProjectsScreen(
//    navController: NavHostController,
//    projects: List<PortfolioItem>,
//    onProjectsUpdated: (List<PortfolioItem>) -> Unit,
//    onDone: () -> Unit
//) {
//    val context = LocalContext.current
//    val items = remember { mutableStateListOf<PortfolioItem>().apply { addAll(projects) } }
//
//    val titleState = rememberSaveable { mutableStateOf("") }
//    val descState = rememberSaveable { mutableStateOf("") }
//    val linkState = rememberSaveable { mutableStateOf("") }
//    val imageState = rememberSaveable { mutableStateOf<String?>(null) }
//
//    val pickImageLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            imageState.value = uri?.toString()
//        }
//
//    var showExitDialog by remember { mutableStateOf(false) }
//
//    BackHandler {
//        if (navController.previousBackStackEntry != null) {
//            navController.popBackStack()
//        } else {
//            showExitDialog = true
//        }
//    }
//
//    if (showExitDialog) {
//        AlertDialog(
//            onDismissRequest = { showExitDialog = false },
//            confirmButton = {
//                TextButton(onClick = { (context as? ComponentActivity)?.finish() }) {
//                    Text("Exit")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showExitDialog = false }) {
//                    Text("Cancel")
//                }
//            },
//            title = { Text("Exit app?") },
//            text = { Text("Are you sure you want to exit the app?") }
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(12.dp)
//    ) {
//        Text(
//            "Projects",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(8.dp)
//        )
//
//        OutlinedTextField(
//            value = titleState.value,
//            onValueChange = { titleState.value = it },
//            label = { Text("Project title") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(4.dp)
//        )
//        OutlinedTextField(
//            value = descState.value,
//            onValueChange = { descState.value = it },
//            label = { Text("Short description") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(4.dp)
//        )
//        OutlinedTextField(
//            value = linkState.value,
//            onValueChange = { linkState.value = it },
//            label = { Text("Optional link (GitHub, demo)") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(4.dp)
//        )
//
//        Row(
//            modifier = Modifier.padding(4.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Button(onClick = { pickImageLauncher.launch("image/*") }) {
//                Text("Pick Image")
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Button(
//                onClick = {
//                    if (titleState.value.isBlank()) {
//                        Toast.makeText(context, "Please enter project title", Toast.LENGTH_SHORT)
//                            .show()
//                        return@Button
//                    }
//                    val item = PortfolioItem(
//                        title = titleState.value,
//                        description = descState.value,
//                        link = linkState.value.ifBlank { null },
//                        imageUri = imageState.value
//                    )
//                    items.add(item)
//                    titleState.value = ""
//                    descState.value = ""
//                    linkState.value = ""
//                    imageState.value = null
//                    Toast.makeText(context, "Project added", Toast.LENGTH_SHORT).show()
//                }
//            ) {
//                Text("Add Project")
//            }
//        }
//
//        if (imageState.value != null) {
//            Spacer(modifier = Modifier.height(8.dp))
//            ImagePreview(
//                uri = Uri.parse(imageState.value!!),
//                size = 120.dp
//            )
//        }
//
//        Divider(modifier = Modifier.padding(vertical = 8.dp))
//
//        LazyColumn(
//            modifier = Modifier.weight(1f, fill = false)
//        ) {
//            items(items, key = { it.id }) { project ->
//                ProjectCard(project = project, onRemove = { items.remove(project) })
//            }
//        }
//
//        Button(
//            onClick = {
//                onProjectsUpdated(items.toList())
//                Toast.makeText(context, "Projects saved", Toast.LENGTH_SHORT).show()
//                onDone()
//            },
//            modifier = Modifier.align(Alignment.End)
//        ) {
//            Text("Save & Preview")
//        }
//    }
//}
//
//@Composable
//fun ProjectCard(project: PortfolioItem, onRemove: () -> Unit) {
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        shape = MaterialTheme.shapes.medium,
//        elevation = cardElevation(4.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            if (project.imageUri != null) {
//                Image(
//                    painter = rememberAsyncImagePainter(Uri.parse(project.imageUri)),
//                    contentDescription = "Project Image",
//                    modifier = Modifier
//                        .size(80.dp)
//                        .clip(MaterialTheme.shapes.small),
//                    contentScale = ContentScale.Crop
//                )
//            } else {
//                Surface(
//                    modifier = Modifier.size(80.dp),
//                    shape = MaterialTheme.shapes.small,
//                    color = colorScheme.onSurface.copy(alpha = 0.06f)
//                ) {
//                    Box(contentAlignment = Alignment.Center) {
//                        Text("No\nimage", modifier = Modifier.padding(4.dp))
//                    }
//                }
//            }
//
//            Column(
//                modifier = Modifier
//                    .padding(start = 12.dp)
//                    .weight(1f)
//            ) {
//                Text(project.title, fontWeight = FontWeight.Bold)
//                Text(
//                    project.description,
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.Gray
//                )
//                project.link?.let {
//                    Text(
//                        it,
//                        modifier = Modifier.clickable {
//                            try {
//                                val i = Intent(Intent.ACTION_VIEW, Uri.parse(it))
//                                context.startActivity(i)
//                            } catch (_: Exception) {
//                            }
//                        },
//                        color = colorScheme.primary
//                    )
//                }
//            }
//
//            Button(onClick = onRemove) {
//                Text("Remove")
//            }
//        }
//    }
//}
//
//// ---------- Portfolio Screen (Preview) ----------
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PortfolioScreen(
//    navController: NavHostController,
//    profile: UserProfile,
//    projects: List<PortfolioItem>,
//    onEdit: () -> Unit
//) {
//    val context = LocalContext.current
//    var showImageDialog by remember { mutableStateOf<Pair<Boolean, Uri?>>(false to null) }
//    var showExitDialog by remember { mutableStateOf(false) }
//
//    BackHandler {
//        if (navController.previousBackStackEntry != null) {
//            navController.popBackStack()
//        } else {
//            showExitDialog = true
//        }
//    }
//
//    if (showExitDialog) {
//        AlertDialog(
//            onDismissRequest = { showExitDialog = false },
//            confirmButton = {
//                TextButton(onClick = { (context as? ComponentActivity)?.finish() }) {
//                    Text("Exit")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showExitDialog = false }) {
//                    Text("Cancel")
//                }
//            },
//            title = { Text("Exit app?") },
//            text = { Text("Are you sure you want to exit the app?") }
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(12.dp)
//    ) {
//        // Header
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//        ) {
//            Column {
//                Text(
//                    profile.name.ifBlank { "Your Name" },
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = colorScheme.primary
//                )
//                Text(profile.title, style = MaterialTheme.typography.bodyLarge)
//            }
//        }
//
//        // Profile card
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            shape = MaterialTheme.shapes.medium,
//            elevation = cardElevation(6.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                if (profile.profileImageUri != null) {
//                    ImagePreview(uri = Uri.parse(profile.profileImageUri), size = 120.dp)
//                } else {
//                    ProfilePlaceholderLarge()
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//                if (profile.bio.isNotBlank()) {
//                    Text(profile.bio, modifier = Modifier.padding(top = 8.dp))
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(profile.email, style = MaterialTheme.typography.bodySmall)
//            }
//        }
//
//        Text(
//            "Projects",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(8.dp)
//        )
//
//        if (projects.isEmpty()) {
//            Text(
//                "No projects added yet. Go to Projects to add.",
//                color = Color.Gray,
//                modifier = Modifier.padding(8.dp)
//            )
//        } else {
//            LazyColumn(
//                modifier = Modifier.weight(1f)
//            ) {
//                items(projects) { project ->
//                    Card(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .fillMaxWidth(),
//                        shape = MaterialTheme.shapes.medium,
//                        elevation = cardElevation(4.dp)
//                    ) {
//                        Row(modifier = Modifier.padding(12.dp)) {
//                            if (project.imageUri != null) {
//                                Image(
//                                    painter = rememberAsyncImagePainter(
//                                        model = Uri.parse(
//                                            project.imageUri
//                                        )
//                                    ),
//                                    contentDescription = "Project Image small",
//                                    modifier = Modifier
//                                        .size(84.dp)
//                                        .clip(MaterialTheme.shapes.small)
//                                        .clickable {
//                                            showImageDialog = true to Uri.parse(project.imageUri)
//                                        },
//                                    contentScale = ContentScale.Crop
//                                )
//                            } else {
//                                Surface(
//                                    modifier = Modifier.size(84.dp),
//                                    shape = MaterialTheme.shapes.small,
//                                    color = colorScheme.onSurface.copy(alpha = 0.06f)
//                                ) {
//                                    Box(contentAlignment = Alignment.Center) {
//                                        Text("No\nimage", modifier = Modifier.padding(4.dp))
//                                    }
//                                }
//                            }
//
//                            Column(modifier = Modifier.padding(start = 12.dp)) {
//                                Text(project.title, fontWeight = FontWeight.Bold)
//                                Text(
//                                    project.description,
//                                    style = MaterialTheme.typography.bodyMedium,
//                                    color = Color.Gray
//                                )
//                                project.link?.let {
//                                    Text("Link: $it", color = colorScheme.primary)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Bottom buttons: Edit & Export PDF
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(
//                onClick = { onEdit() },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp)
//            ) {
//                Text("Edit")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Button(
//                onClick = {
//                    val pdfFile = generatePdfAndSave(context, profile, projects)
//                    if (pdfFile != null) {
//                        sharePdf(context, pdfFile)
//                    } else {
//                        Toast.makeText(context, "Could not create PDF", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp)
//            ) {
//                Text("Export as PDF")
//            }
//        }
//    }
//
//    if (showImageDialog.first && showImageDialog.second != null) {
//        ImageFullScreenDialog(uri = showImageDialog.second!!) {
//            showImageDialog = false to null
//        }
//    }
//}
//
//// ---------- Small UI helpers ----------
//@Composable
//private fun ProfilePlaceholderLarge() {
//    Surface(
//        modifier = Modifier.size(120.dp),
//        shape = CircleShape,
//        color = colorScheme.onSurface.copy(alpha = 0.06f)
//    ) {
//        Box(contentAlignment = Alignment.Center) {
//            Text("Profile", modifier = Modifier.padding(8.dp))
//        }
//    }
//}
//
//@Composable
//fun ImagePreview(uri: Uri, size: Dp) {
//    Image(
//        painter = rememberAsyncImagePainter(model = uri),
//        contentDescription = "Image",
//        modifier = Modifier
//            .size(size)
//            .clip(MaterialTheme.shapes.small),
//        contentScale = ContentScale.Crop
//    )
//}
//
//@Composable
//fun ImageFullScreenDialog(uri: Uri, onClose: () -> Unit) {
//    val context = LocalContext.current
//    androidx.compose.ui.window.Dialog(onDismissRequest = onClose) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black)
//        ) {
//            Image(
//                painter = rememberAsyncImagePainter(model = uri),
//                contentDescription = "Full Image",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Fit
//            )
//            IconButton(
//                onClick = onClose,
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//                    .padding(16.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
//                    contentDescription = "Close",
//                    tint = Color.White
//                )
//            }
//            IconButton(
//                onClick = {
//                    try {
//                        val shareIntent = Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(Intent.EXTRA_STREAM, uri)
//                            type = "image/*"
//                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                        }
//                        context.startActivity(
//                            Intent.createChooser(
//                                shareIntent,
//                                "Share image"
//                            )
//                        )
//                    } catch (_: Exception) {
//                    }
//                },
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(16.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = android.R.drawable.ic_menu_share),
//                    contentDescription = "Share",
//                    tint = Color.White
//                )
//            }
//        }
//    }
//}
//
//// ---------- PDF generation & sharing ----------
//fun generatePdfAndSave(
//    context: Context,
//    profile: UserProfile,
//    projects: List<PortfolioItem>
//): File? {
//    return try {
//        val doc = PdfDocument()
//        val pageWidth = 595
//        val pageHeight = 842
//
//        val paint = Paint()
//        var yPosition = 24f
//
//        // first page - profile
//        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
//        val page = doc.startPage(pageInfo)
//        val canvas: Canvas = page.canvas
//
//        paint.textSize = 18f
//        paint.isFakeBoldText = true
//        canvas.drawText(profile.name.ifBlank { "Your Name" }, 24f, yPosition, paint)
//        paint.isFakeBoldText = false
//        paint.textSize = 12f
//        yPosition += 24f
//        canvas.drawText(profile.title, 24f, yPosition, paint)
//        yPosition += 18f
//        canvas.drawText(profile.email, 24f, yPosition, paint)
//        yPosition += 18f
//
//        if (profile.bio.isNotBlank()) {
//            paint.textSize = 11f
//            val bioLines = wrapText(profile.bio, 80)
//            for (line in bioLines) {
//                canvas.drawText(line, 24f, yPosition, paint)
//                yPosition += 14f
//            }
//        }
//
//        // profile image
//        profile.profileImageUri?.let { uriStr ->
//            val uri = Uri.parse(uriStr)
//            val bmp = loadBitmapFromUri(context, uri)
//            bmp?.let {
//                val scaled = scaleBitmapToWidth(it, 120)
//                canvas.drawBitmap(
//                    scaled,
//                    (pageWidth - scaled.width - 24).toFloat(),
//                    24f,
//                    paint
//                )
//            }
//        }
//
//        doc.finishPage(page)
//
//        // project pages
//        var pageNumber = 2
//        var currentY = 40f
//        var currentPage = doc.startPage(
//            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
//        )
//        var c = currentPage.canvas
//        paint.textSize = 14f
//
//        for (project in projects) {
//            if (currentY > pageHeight - 120) {
//                doc.finishPage(currentPage)
//                pageNumber++
//                currentPage = doc.startPage(
//                    PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
//                )
//                c = currentPage.canvas
//                currentY = 40f
//            }
//
//            paint.isFakeBoldText = true
//            paint.textSize = 13f
//            c.drawText(project.title, 24f, currentY, paint)
//            paint.isFakeBoldText = false
//            currentY += 18f
//
//            project.imageUri?.let { imgStr ->
//                val bmp = loadBitmapFromUri(context, imgStr.toUri())
//                bmp?.let {
//                    val scaled = scaleBitmapToWidth(it, 160)
//                    c.drawBitmap(scaled, 24f, currentY, paint)
//                    paint.textSize = 11f
//                    val descLines = wrapText(project.description, 40)
//                    var descY = currentY + 12f
//                    for (line in descLines) {
//                        c.drawText(line, (24f + scaled.width + 12f), descY, paint)
//                        descY += 12f
//                    }
//                    currentY += scaled.height + 16f
//                } ?: run {
//                    paint.textSize = 11f
//                    val descLines = wrapText(project.description, 90)
//                    for (line in descLines) {
//                        c.drawText(line, 24f, currentY, paint)
//                        currentY += 12f
//                    }
//                    currentY += 8f
//                }
//            } ?: run {
//                paint.textSize = 11f
//                val descLines = wrapText(project.description, 90)
//                for (line in descLines) {
//                    c.drawText(line, 24f, currentY, paint)
//                    currentY += 12f
//                }
//                currentY += 8f
//            }
//
//            project.link?.let {
//                paint.textSize = 11f
//                paint.color = android.graphics.Color.BLUE
//                c.drawText("Link: $it", 24f, currentY, paint)
//                paint.color = android.graphics.Color.BLACK
//                currentY += 18f
//            }
//            currentY += 12f
//        }
//
//        doc.finishPage(currentPage)
//
//        val file = File(context.cacheDir, "portfolio_${System.currentTimeMillis()}.pdf")
//        val fos = FileOutputStream(file)
//        doc.writeTo(fos)
//        doc.close()
//        fos.close()
//        file
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
//
//fun wrapText(text: String, maxCharsPerLine: Int): List<String> {
//    val words = text.split("\\s+".toRegex())
//    val lines = mutableListOf<String>()
//    var current = StringBuilder()
//    for (w in words) {
//        if (current.length + w.length + 1 > maxCharsPerLine) {
//            lines.add(current.toString())
//            current = StringBuilder()
//        }
//        if (current.isNotEmpty()) current.append(" ")
//        current.append(w)
//    }
//    if (current.isNotEmpty()) lines.add(current.toString())
//    return lines
//}
//
//fun scaleBitmapToWidth(bmp: Bitmap, targetWidth: Int): Bitmap {
//    val ratio = targetWidth.toFloat() / bmp.width.toFloat()
//    val targetHeight = (bmp.height * ratio).toInt()
//    return bmp.scale(targetWidth, targetHeight)
//}
//
//fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
//    return try {
//        val input: InputStream? = context.contentResolver.openInputStream(uri)
//        BitmapFactory.decodeStream(input).also { input?.close() }
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
//
//fun sharePdf(context: Context, file: File) {
//    try {
//        val authority = context.packageName + ".fileprovider"
//        val contentUri: Uri = FileProvider.getUriForFile(context, authority, file)
//        val shareIntent = Intent(Intent.ACTION_SEND).apply {
//            type = "application/pdf"
//            putExtra(Intent.EXTRA_STREAM, contentUri)
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        }
//        context.startActivity(Intent.createChooser(shareIntent, "Share PDF"))
//    } catch (e: Exception) {
//        e.printStackTrace()
//        Toast.makeText(
//            context,
//            "Unable to share PDF. Make sure FileProvider is configured.",
//            Toast.LENGTH_LONG
//        ).show()
//    }
//}
//
//// ---------- Compose Theme (Material 3 – professional look) ----------
//
//@Composable
//fun PortfolioTheme(content: @Composable () -> Unit) {
//    val lightColors = lightColorScheme(
//        primary = Color(0xFF1565C0),
//        secondary = Color(0xFF4FC3F7),
//        tertiary = Color(0xFF81D4FA),
//        background = Color(0xFFF7F9FC),
//        surface = Color.White,
//        onPrimary = Color.White,
//        onSecondary = Color.White,
//        onSurface = Color(0xFF1A1A1A)
//    )
//
//    val shapes = Shapes(
//        small = RoundedCornerShape(12.dp),
//        medium = RoundedCornerShape(18.dp),
//        large = RoundedCornerShape(26.dp)
//    )
//
//    val typography = Typography(
//        headlineMedium = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.SemiBold),
//        titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
//        bodyLarge = TextStyle(fontSize = 16.sp),
//        bodyMedium = TextStyle(fontSize = 14.sp),
//        bodySmall = TextStyle(fontSize = 12.sp),
//        labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
//    )
//
//    MaterialTheme(
//        colorScheme = lightColors,
//        typography = typography,
//        shapes = shapes,
//        content = content
//    )
//}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PortfolioTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Profile.route
                    ) {
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController = navController)
                        }
                        composable(Screen.Projects.route) {
                            ProjectsScreen(navController = navController)
                        }
                        composable(Screen.Portfolio.route) {
                            PortfolioScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}