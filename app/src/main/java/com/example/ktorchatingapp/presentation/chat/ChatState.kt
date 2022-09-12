package com.example.ktorchatingapp.presentation.chat

import com.example.ktorchatingapp.domain.model.Message

data class ChatState(
    val loading: Boolean = false,
    val data: List<Message> = emptyList()
)
