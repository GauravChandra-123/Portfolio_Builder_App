package com.example.bizzcardapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.portfolioDataStore by preferencesDataStore(name = "portfolio_prefs")
