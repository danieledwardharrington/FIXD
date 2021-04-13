package com.fixdapp.internal.spacebook.api.models.individual.github

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubPushModel(@SerialName("branch") val branch: String
): GithubEventModel()
