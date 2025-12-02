package com.example.bizzcardapp.model

data class UserProfile(
    val name: String = "",
    val title: String = "",
    val email: String = "",
    val bio: String = "",
    val profileImageUri: String? = null // saved as String
)