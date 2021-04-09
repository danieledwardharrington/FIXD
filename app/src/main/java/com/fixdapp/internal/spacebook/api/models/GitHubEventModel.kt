package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubEventModel(
    @SerialName("githubId")
    val githubId: String,

    @SerialName("url")
    val url: String,

    @SerialName("branch")
    val branch: String,

    @SerialName("pullRequestNumber")
    val pullRequestNumber: Int
)
