package com.takaotech.route.github.model

import kotlinx.serialization.Serializable

@Serializable
data class GHUser(
    val id: Long,
    val name: String,
    val url: String
)