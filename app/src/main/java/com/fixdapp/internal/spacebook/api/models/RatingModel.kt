package com.fixdapp.internal.spacebook.api.models

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
    val rating: Int,

    @SerialName("ratedAt")
    val ratedAt: String
)
