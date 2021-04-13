package com.fixdapp.internal.spacebook.api.models.individual.github

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
The new PR and merged PR JSON objects appear to have the same members so
just having this one model for both should be fine
 */

@Serializable
data class GithubPRModel(
    @SerialName("pullRequestNumber") val pullRequestNumber: Int
): GithubEventModel()
