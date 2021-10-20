package com.example.gittrendingapi.models

import java.io.Serializable

data class ApiResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)