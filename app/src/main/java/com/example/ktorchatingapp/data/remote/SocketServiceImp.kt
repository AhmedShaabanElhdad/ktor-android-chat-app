package com.example.ktorchatingapp.data.remote

import com.example.ktorchatingapp.data.remote.core.EndPoint
import com.example.ktorchatingapp.data.remote.dto.MessageDTO
import com.example.ktorchatingapp.domain.SocketService
import com.example.ktorchatingapp.domain.core.Resource
import com.example.ktorchatingapp.domain.model.Message
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SocketServiceImp(
    private val httpClient: HttpClient
) : SocketService {

    var socket: WebSocketSession? = null

    override suspend fun initializeService(username: String): Resource<Unit> {
        return try {
            socket = httpClient.webSocketSession {
                url("${EndPoint.ChatSocket.url}?username=$username")
            }
            if (socket?.isActive == true)
                Resource.Success(Unit)
            else
                Resource.Error("couldn't establish connection with the server")
        } catch (e: Exception) {
            Resource.Error(e.message ?:"unknown error")
        }

    }

    override suspend fun observeOnMessage(): Flow<Message> {
        return try {
            socket?.incoming
                ?.consumeAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val jsonMessage = (it as Frame.Text).readText()
                    val text = Json.decodeFromString<MessageDTO>(jsonMessage)
                    text.toMessage()
                } ?: flowOf()
        }catch (e:Exception){
            flowOf()
        }
    }

    override suspend fun sendMessage(message: String) {
        socket?.send(Frame.Text(message))
    }

    override suspend fun disconnect() {
        socket?.close()
    }
}