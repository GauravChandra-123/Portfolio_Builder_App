package com.example.bizzcardapp.ui.portfolio


import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bizzcardapp.ui.components.ImagePreview
import com.example.bizzcardapp.ui.navigation.Screen
import com.example.bizzcardapp.utils.generatePdfAndSave
import com.example.bizzcardapp.utils.sharePdf
import androidx.core.net.toUri


@Composable
fun PortfolioScreen(
    navController: NavHostController,
    context: Context = LocalContext.current,
    vm: PortfolioViewModel = viewModel()
) {
    val profile by vm.profile.collectAsState()
    val projects by vm.projects.collectAsState()

    LaunchedEffect(Unit) { vm.loadAll() }

    Column(Modifier.fillMaxSize().padding(12.dp)) {

        Text(profile.name, style = MaterialTheme.typography.headlineMedium)
        Text(profile.title)

        Spacer(Modifier.height(12.dp))

        profile.profileImageUri?.let {
            ImagePreview(uri = Uri.parse(it), size = 130.dp)
        }

        Spacer(Modifier.height(16.dp))

        Text("Projects", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(Modifier.weight(1f)) {
            items(projects.size) { i ->
                val p = projects[i]
                Card(Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(p.title, style = MaterialTheme.typography.titleLarge)
                        Text(p.description)
                        p.imageUri?.let { ImagePreview(uri = it.toUri(), size = 100.dp) }
                    }
                }
            }
        }

        /*Button(
            onClick = { navController.navigate(Screen.Projects.route) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Edit") }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                val pdf = generatePdfAndSave(context, profile, projects)
                pdf?.let { sharePdf(context, it) }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Export PDF") }*/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()        // <-- This prevents cutting
                .padding(16.dp)
        ) {
            Button(
                onClick = { navController.navigate(Screen.Profile.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val pdf = generatePdfAndSave(context, profile, projects)
                    if (pdf != null) sharePdf(context, pdf)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Export PDF")
            }
        }
    }
}
