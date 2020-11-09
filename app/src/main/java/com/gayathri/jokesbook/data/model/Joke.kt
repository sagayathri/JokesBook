package com.gayathri.jokesbook.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Joke (
    @SerialName("id")
    val id: Int,

    @SerialName("type")
    val type: String,

    @SerialName("setup")
    val setup: String,

    @SerialName("punchline")
    val punchline: String
)
