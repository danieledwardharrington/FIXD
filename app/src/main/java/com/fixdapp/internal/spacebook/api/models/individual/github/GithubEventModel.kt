package com.fixdapp.internal.spacebook.api.models.individual.github

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class GithubEventModel {
    @SerialName("githubId") val githubId: String = ""

    @SerialName("url") val url: String = ""

    @SerialName("repository") val repo: String = ""
}