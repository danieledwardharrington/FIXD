package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationModel<T>(
    @SerialName("currentPage")
    val currentPage: Int,

    @SerialName("perPage")
    val perPage: Int,

    @SerialName("totalPages")
    val totalPages: Int,

    @SerialName("totalEntries")
    val totalEntries: Int,

    @SerialName("data")
    val data: T?
)