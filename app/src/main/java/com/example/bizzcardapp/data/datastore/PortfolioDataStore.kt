package com.example.bizzcardapp.data.datastore

/*
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.bizzcardapp.model.PortfolioItem
import com.example.bizzcardapp.model.UserProfile

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

class PortfolioDataStore(private val context: Context) {

    private val gson = Gson()

    private val dataStore = context.portfolioDataStore   // ✅ Use the global singleton


    private val PREFS_NAME = "portfolio_prefs"
    private val Context.ds by preferencesDataStore(name = PREFS_NAME)

    private val KEY_PROFILE = stringPreferencesKey("user_profile_json")
    private val KEY_PROJECTS = stringPreferencesKey("projects_json")

    suspend fun saveProfile(profile: UserProfile) {
        val json = gson.toJson(profile)
        context.ds.edit { it[KEY_PROFILE] = json }
    }

    suspend fun loadProfile(): UserProfile {
        val prefs = context.ds.data.first()
        val json = prefs[KEY_PROFILE] ?: return UserProfile()
        return try { gson.fromJson(json, UserProfile::class.java) } catch(_: Exception) {
            UserProfile()
        }
    }

    suspend fun saveProjects(list: List<PortfolioItem>) {
        val json = gson.toJson(list)
        context.ds.edit { it[KEY_PROJECTS] = json }
    }

    suspend fun loadProjects(): List<PortfolioItem> {
        val prefs = context.ds.data.first()
        val json = prefs[KEY_PROJECTS] ?: return emptyList()
        return try {
            gson.fromJson(json, object : TypeToken<List<PortfolioItem>>(){}.type)
        } catch(_: Exception) {
            emptyList()
        }
    }
}*/

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.bizzcardapp.model.PortfolioItem
import com.example.bizzcardapp.model.UserProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

class PortfolioDataStore(private val context: Context) {

    private val gson = Gson()
    private val dataStore = context.portfolioDataStore   // ✅ Use the global singleton

    private val KEY_PROFILE = stringPreferencesKey("user_profile_json")
    private val KEY_PROJECTS = stringPreferencesKey("projects_json")

    suspend fun saveProfile(profile: UserProfile) {
        val json = gson.toJson(profile)
        dataStore.edit { it[KEY_PROFILE] = json }
    }

    suspend fun loadProfile(): UserProfile {
        val prefs = dataStore.data.first()
        val json = prefs[KEY_PROFILE] ?: return UserProfile()
        return try {
            gson.fromJson(json, UserProfile::class.java)
        } catch (_: Exception) {
            UserProfile()
        }
    }

    suspend fun saveProjects(list: List<PortfolioItem>) {
        val json = gson.toJson(list)
        dataStore.edit { it[KEY_PROJECTS] = json }
    }

    suspend fun loadProjects(): List<PortfolioItem> {
        val prefs = dataStore.data.first()
        val json = prefs[KEY_PROJECTS] ?: return emptyList()
        return try {
            gson.fromJson(json, object : TypeToken<List<PortfolioItem>>(){}.type)
        } catch (_: Exception) {
            emptyList()
        }
    }
}

