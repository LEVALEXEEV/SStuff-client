package com.example.sstuff.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val description: String,
    val imageUrls: List<String>
)