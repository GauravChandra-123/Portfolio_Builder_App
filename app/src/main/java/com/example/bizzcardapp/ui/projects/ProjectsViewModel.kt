package com.example.bizzcardapp.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bizzcardapp.model.PortfolioItem
import com.example.bizzcardapp.repository.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectsViewModel(
    private val repo: PortfolioRepository
) : ViewModel() {

    private val _projects = MutableStateFlow<List<PortfolioItem>>(emptyList())
    val projects = _projects.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _projects.value = repo.loadProjects()
        }
    }

    fun addProject(item: PortfolioItem) {
        val updated = _projects.value + item
        _projects.value = updated
        save(updated)
    }

    fun removeProject(id: String) {
        val updated = _projects.value.filterNot { it.id == id }
        _projects.value = updated
        save(updated)
    }

    private fun save(list: List<PortfolioItem>) {
        viewModelScope.launch {
            repo.saveProjects(list)
        }
    }
}