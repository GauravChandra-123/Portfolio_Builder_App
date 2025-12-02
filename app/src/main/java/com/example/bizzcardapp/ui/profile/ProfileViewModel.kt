package com.example.bizzcardapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bizzcardapp.AppContext
import com.example.bizzcardapp.data.datastore.PortfolioDataStore
import com.example.bizzcardapp.model.UserProfile
import com.example.bizzcardapp.repository.PortfolioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/*
class ProfileViewModel(
    private val repo: PortfolioRepository
) : ViewModel() {

    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile

    fun load() {
        viewModelScope.launch {
            _profile.value = repo.loadProfile()
        }
    }

    fun updateProfile(updated: UserProfile) {
        _profile.value = updated
        viewModelScope.launch {
            repo.saveProfile(updated)
        }
    }
}*/
class ProfileViewModel : ViewModel() {

    private val repo = PortfolioRepository(PortfolioDataStore(AppContext.instance))

    private val _profile = MutableStateFlow(UserProfile())
    val profile: StateFlow<UserProfile> = _profile

    fun load() {
        viewModelScope.launch {
            _profile.value = repo.loadProfile()
        }
    }

    fun updateProfile(updated: UserProfile) {
        viewModelScope.launch {
            repo.saveProfile(updated)
            _profile.value = updated
        }
    }
}
