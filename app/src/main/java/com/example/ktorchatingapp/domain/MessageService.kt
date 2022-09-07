package com.example.ktorchatingapp.domain

import com.example.ktorchatingapp.data.remote.dto.MessageDTO
import com.example.ktorchatingapp.domain.model.Message

interface MessageService {

    suspend fun getAllMessage(): List<Message>

}

