package com.fixdapp.internal.spacebook.api.models.individual

import kotlinx.serialization.Serializable

@Serializable
data class SessionRequestModel(val email: String, val password: String)
