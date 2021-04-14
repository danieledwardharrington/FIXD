package com.fixdapp.internal.spacebook.api.models.feed


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserModel (
    @SerialName("id")
    val id: Int,

    @SerialName("email")
    val email: String,

    @SerialName("name")
    val name: String,

    @SerialName("registeredAt")
    val registeredAt: String,

    @SerialName("githubUsername")
    val githubUserName: String?,

    @SerialName("rating")
    val rating: Double? = 0.0
): Parcelable
