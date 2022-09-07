package com.example.ktorchatingapp.di

import com.example.ktorchatingapp.domain.MessageService
import com.example.ktorchatingapp.data.remote.MessageServiceImp
import com.example.ktorchatingapp.domain.SocketService
import com.example.ktorchatingapp.data.remote.SocketServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient():HttpClient {
        return HttpClient(CIO){
            install(Logging)
            install(WebSockets)
            install(JsonFeature){
                serializer = KotlinxSerializer()
            }
        }
    }



    @Provides
    @Singleton
    fun provideMessageService(client:HttpClient): MessageService {
        return MessageServiceImp(client)
    }
    @Provides
    @Singleton
    fun provideSocketService(client:HttpClient): SocketService {
        return SocketServiceImp(client)
    }
}