package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class FeedEnum {
    @SerialName("NEW_POST")
    NEW_POST,

    @SerialName("NEW_COMMENT")
    NEW_COMMENT,

    @SerialName("HIGH_RATING")
    HIGH_RATING,

    @SerialName("GITHUB_NEW_REPO")
    GITHUB_NEW_REPO,

    @SerialName("GITHUB_NEW_PR")
    GITHUB_NEW_PR,

    @SerialName("GITHUB_MERGED_PR")
    GITHUB_MERGED_PR,

    @SerialName("GITHUB_PUSH")
    GITHUB_PUSH
}