package com.example.bizzcardapp.ui.projects


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bizzcardapp.repository.PortfolioRepository

class ProjectsViewModelFactory(
    private val repo: PortfolioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
