package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.Serializable

@Serializable
data class SessionRequestModel(val email: String, val password: String)
