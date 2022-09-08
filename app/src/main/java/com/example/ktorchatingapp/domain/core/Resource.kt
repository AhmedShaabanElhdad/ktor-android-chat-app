package com.example.ktorchatingapp.domain.core

sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Error(val exception: String): Resource<Nothing>()
//    object Loading: Resource<Nothing>()

    fun toData(): T? = if(this is Success) this.data else null
}