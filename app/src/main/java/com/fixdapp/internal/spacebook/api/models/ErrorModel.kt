package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorModel(
    @SerialName("type")
    val type: String,

    @SerialName("message")
    val message: String
)
