package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityModel(
    @SerialName("id")
    val id: Int,

    @SerialName("userId")
    val userId: Int,

    @SerialName("occurredAt")
    val occurredAt: String,

    @SerialName("type")
    val type: String,


)
