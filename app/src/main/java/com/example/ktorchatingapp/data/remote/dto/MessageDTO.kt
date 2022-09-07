package com.example.ktorchatingapp.data.remote.dto

import com.example.ktorchatingapp.domain.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat

@Serializable
data class MessageDTO(
    val id: String,
    val timsamp: Long,
    val message: String,
    val username: String
) {



    private val formattedDate: String =
        DateFormat.getDateInstance(DateFormat.DEFAULT).format(timsamp)

    fun toMessage() = Message(
        username = username,
        message = message,
        date = formattedDate
    )
}
