package com.example.ktorchatingapp.data.remote.core

const val BASE_URL = "http://192.168.1.136:8080"
const val SOCKET_URL = "ws://192.168.1.136:8080"

sealed class EndPoint(val url: String) {
    object GetMessages : EndPoint("${BASE_URL}/messages")
    object ChatSocket : EndPoint("${SOCKET_URL}/chatting-sockets")
}