package com.example.bizzcardapp.ui.navigation

sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object Projects : Screen("projects")
    object Portfolio : Screen("portfolio")
}