package com.example.ktorchatingapp.domain

import com.example.ktorchatingapp.domain.core.Resource
import com.example.ktorchatingapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface SocketService {

    suspend fun initializeService(
        username:String
    ): Resource<Unit>

    suspend fun observeOnMessage():Flow<Message>

    suspend fun sendMessage(message:String)

    suspend fun disconnect()

}

