package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class CommentModel(
    @SerialName("id")
    val id: Int,

    @SerialName("message")
    val message: String,

    @SerialName("userId")
    val userId: Int,

    @SerialName("postId")
    val postId: Int,

    @SerialName("commentedAt")
    val commentedAt: String
)