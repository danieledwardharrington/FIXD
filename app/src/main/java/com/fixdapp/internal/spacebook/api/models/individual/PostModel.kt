package com.fixdapp.internal.spacebook.api.models.individual

import android.os.Parcelable
import com.fixdapp.internal.spacebook.api.models.feed.UserModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
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
): Parcelable {
    var numComments: Int? = null
}
