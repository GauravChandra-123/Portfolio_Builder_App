package com.example.bizzcardapp.repository

import com.example.bizzcardapp.AppContext
import com.example.bizzcardapp.data.datastore.PortfolioDataStore
import com.example.bizzcardapp.model.PortfolioItem
import com.example.bizzcardapp.model.UserProfile

class PortfolioRepository(private val dataStore: PortfolioDataStore) {

    suspend fun saveProfile(profile: UserProfile) {
        dataStore.saveProfile(profile)
    }

    suspend fun loadProfile(): UserProfile {
        return dataStore.loadProfile()
    }

    suspend fun saveProjects(list: List<PortfolioItem>) {
        dataStore.saveProjects(list)
    }

    suspend fun loadProjects(): List<PortfolioItem> {
        return dataStore.loadProjects()
    }
    companion object {
        fun default(): PortfolioRepository {
            return PortfolioRepository(PortfolioDataStore(AppContext.instance))
        }
    }

}