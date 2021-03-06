package com.fixdapp.internal.spacebook.api.models.individual

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingModel(
    @SerialName("id")
    val id: Int,

    @SerialName("userId")
    val userId: Int,

    @SerialName("raterId")
    val raterId: Int,

    @SerialName("rating")
    val rating: Double,

    @SerialName("ratedAt")
    val ratedAt: String
)
