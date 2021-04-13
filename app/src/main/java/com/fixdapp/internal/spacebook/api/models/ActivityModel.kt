package com.fixdapp.internal.spacebook.api.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Encoder

@Serializable
data class ActivityModel(
    @SerialName("id")
    val id: Int,

    @SerialName("userId")
    val userId: Int,

    @SerialName("occurredAt")
    val occurredAt: String,

    @SerialName("type")
    val type: FeedEnum,

    @SerialName("data")
    val data: FeedEventData
)
