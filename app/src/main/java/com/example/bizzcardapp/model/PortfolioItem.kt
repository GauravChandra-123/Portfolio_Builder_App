package com.example.bizzcardapp.model

import java.util.UUID

data class PortfolioItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val link: String? = null,
    val imageUri: String? = null
)