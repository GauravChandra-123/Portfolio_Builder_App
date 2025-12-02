package com.example.bizzcardapp.ui.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bizzcardapp.model.PortfolioItem
import com.example.bizzcardapp.model.UserProfile
import com.example.bizzcardapp.repository.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PortfolioViewModel(
    private val repo: PortfolioRepository = PortfolioRepository.default()
) : ViewModel() {

    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile

    private val _projects = MutableStateFlow<List<PortfolioItem>>(emptyList())
    val projects: StateFlow<List<PortfolioItem>> = _projects

    fun loadAll() {
        viewModelScope.launch {
            _profile.value = repo.loadProfile()
            _projects.value = repo.loadProjects()
        }
    }
}