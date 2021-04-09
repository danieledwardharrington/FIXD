package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("body")
    val body: String,

    @SerialName("postedAt")
    val postedAt: String,

    @SerialName("author")
    val author: UserModel
)
