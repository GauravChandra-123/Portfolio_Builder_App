package com.example.bizzcardapp.ui.projects

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bizzcardapp.model.PortfolioItem
import com.example.bizzcardapp.ui.components.ImagePreview
import com.example.bizzcardapp.ui.navigation.Screen
import androidx.core.net.toUri
import com.example.bizzcardapp.data.datastore.PortfolioDataStore
import com.example.bizzcardapp.repository.PortfolioRepository


@Composable
///*fun ProjectsScreen(
//    navController: NavHostController,
//) {
//    val context = LocalContext.current
//    val portfolioDataStore = PortfolioDataStore(context)   // ✅ correct
//    val repo = PortfolioRepository(portfolioDataStore)
//
//    val vm: ProjectsViewModel = viewModel(
//        factory = ProjectsViewModelFactory(repo)
//    )
//
//    val projects by vm.projects.collectAsState()
//
//    LaunchedEffect(Unit) {
//        vm.load()
//    }
//
//    var title by remember { mutableStateOf("") }
//    var desc by remember { mutableStateOf("") }
//    var link by remember { mutableStateOf("") }
//    var imgUri by remember { mutableStateOf<String?>(null) }
//
//    val pickImage = rememberLauncherForActivityResult(
//        ActivityResultContracts.GetContent()
//    ) { uri -> imgUri = uri?.toString() }
//
//    BackHandler { navController.popBackStack() }
//
//    Column(Modifier.fillMaxSize().padding(12.dp)) {
//        Text("Projects", style = MaterialTheme.typography.headlineMedium)
//
//        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
//        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") })
//        OutlinedTextField(value = link, onValueChange = { link = it }, label = { Text("Link") })
//
//        Row {
//            Button(onClick = { pickImage.launch("image/*") }) { Text("Pick Image") }
//            Spacer(Modifier.width(8.dp))
//            Button(onClick = {
//                if (title.isBlank()) {
//                    Toast.makeText(context, "Enter title", Toast.LENGTH_SHORT).show()
//                    return@Button
//                }
//
//                vm.addProject(
//                    PortfolioItem(
//                        title = title,
//                        description = desc,
//                        link = link.ifBlank { null },
//                        imageUri = imgUri
//                    )
//                )
//
//                title = ""
//                desc = ""
//                link = ""
//                imgUri = null
//
//            }) { Text("Add") }
//        }
//
//        imgUri?.let {
//            ImagePreview(uri = it.toUri(), size = 120.dp)
//        }
//
//        LazyColumn(Modifier.weight(1f)) {
//            items(projects.size) { index ->
//                val project = projects[index]
//                Card(
//                    modifier = Modifier.fillMaxWidth().padding(8.dp),
//                ) {
//                    Column(Modifier.padding(12.dp)) {
//                        Text(project.title, style = MaterialTheme.typography.titleLarge)
//                        Text(project.description)
//                        project.imageUri?.let {
//                            Spacer(Modifier.height(8.dp))
//                            ImagePreview(uri = it.toUri(), size = 100.dp)
//                        }
//                        Button(onClick = { vm.removeProject(project.id) }) {
//                            Text("Remove")
//                        }
//                    }
//                }
//            }
//        }
//
//        Button(
//            onClick = { navController.navigate(Screen.Portfolio.route) },
//            modifier = Modifier.fillMaxWidth()
//        ) { Text("Save & Preview") }
//    }
//}*/
fun ProjectsScreen(

    navController: NavHostController,
   // vm: ProjectsViewModel = viewModel()
) {
    val context = LocalContext.current

    // Create DataStore, Repository, and ViewModel ONCE
    val vm: ProjectsViewModel = viewModel(
        factory = ProjectsViewModelFactory(
            PortfolioRepository(
                PortfolioDataStore(context)
            )
        )
    )

    //val context = LocalContext.current
    val projects by vm.projects.collectAsState()

    LaunchedEffect(Unit) { vm.load() }

    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var imgUri by remember { mutableStateOf<String?>(null) }

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> imgUri = uri?.toString() }

    BackHandler { navController.popBackStack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            "Projects",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = link,
            onValueChange = { link = it },
            label = { Text("Link") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { pickImage.launch("image/*") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Pick Image")
            }

            Spacer(Modifier.width(12.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        Toast.makeText(context, "Enter title", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    vm.addProject(
                        PortfolioItem(
                            title = title,
                            description = desc,
                            link = link.ifBlank { null },
                            imageUri = imgUri
                        )
                    )

                    title = ""
                    desc = ""
                    link = ""
                    imgUri = null
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Add")
            }
        }

        imgUri?.let {
            Spacer(Modifier.height(12.dp))
            ImagePreview(uri = it.toUri(), size = 120.dp)
        }

        Spacer(Modifier.height(16.dp))

        projects.forEach { project ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(project.title, style = MaterialTheme.typography.titleLarge)
                    Text(project.description)
                    project.imageUri?.let {
                        Spacer(Modifier.height(8.dp))
                        ImagePreview(uri = it.toUri(), size = 100.dp)
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { vm.removeProject(project.id) }) {
                        Text("Remove")
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate(Screen.Portfolio.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save & Preview")
        }

        Spacer(Modifier.height(30.dp))  // extra space to avoid bottom cut
    }
}
