package com.example.ktorchatingapp.data.remote

import com.example.ktorchatingapp.data.remote.core.EndPoint
import com.example.ktorchatingapp.data.remote.dto.MessageDTO
import com.example.ktorchatingapp.domain.MessageService
import com.example.ktorchatingapp.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*

class MessageServiceImp(
    private val client:HttpClient
) : MessageService {
    override suspend fun getAllMessage(): List<Message> {
        return try {
            client.get<List<MessageDTO>>(EndPoint.GetMessages.url)
                .map { it.toMessage() }
        }catch (e:Exception){
            emptyList()
        }


    }
}