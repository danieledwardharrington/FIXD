package com.fixdapp.internal.spacebook.api.response

import com.fixdapp.internal.spacebook.api.models.feed.ErrorModel
import com.fixdapp.internal.spacebook.api.models.feed.PaginationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpacebookResponse<T>(
    @SerialName("status")
    val status: String,

    @SerialName("data")
    val data: T?,

    @SerialName("error")
    val error: ErrorModel? = null,

    @SerialName("pagination")
    val pagination: PaginationModel? = null
)
