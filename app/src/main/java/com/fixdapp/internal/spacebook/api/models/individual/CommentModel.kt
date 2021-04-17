package com.fixdapp.internal.spacebook.api.models.individual

import com.fixdapp.internal.spacebook.api.models.feed.UserModel
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
) {
    var post: PostModel? = null

    var commentAuthor: UserModel? = null
}