package com.gayathri.jokesbook.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Jokes(
    val jokes: List<Joke>? = null
)