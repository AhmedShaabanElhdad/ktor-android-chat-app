package com.example.ktorchatingapp.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktorchatingapp.domain.MessageService
import com.example.ktorchatingapp.domain.SocketService
import com.example.ktorchatingapp.domain.core.Resource
import com.example.ktorchatingapp.domain.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val socketService: SocketService,
    private val messageService: MessageService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _message = mutableStateOf("")
    val message: State<String>
        get() = _message

    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState>
        get() = _state

    private val _toast = MutableSharedFlow<String>()
    val toast :SharedFlow<String>
        get() = _toast

    fun joinToChat() {
        getAllMessages()
        initSession()
    }

    private fun initSession(){
        savedStateHandle.get<String>("username")?.let {  username ->
            viewModelScope.launch {
                when(val result = socketService.initializeService(username)){
                    is Resource.Success -> {
                        socketService.observeOnMessage().onEach{ message ->
                            val newList = state.value.data.toMutableList().apply {
                                add(0,message)
                            }
                            _state.value = state.value.copy(data = newList)

                        }.launchIn(viewModelScope)
                    }
                    is Resource.Error -> {
                        _toast.emit(result.exception)
                    }

                }
            }

        }
    }

    fun send() {
        viewModelScope.launch {
            if (_message.value.isNotBlank())
                socketService.sendMessage(_message.value)
        }
    }

    private fun getAllMessages(){
        viewModelScope.launch {
            _state.value = state.value.copy(loading = true)
            val messages = messageService.getAllMessage()
            _state.value = state.value.copy(
                data = messages,
                loading = false
            )
        }
    }

    fun disconnect(){
        viewModelScope.launch {
            socketService.disconnect()
        }
    }


    fun onMessageChange(message: String){
        _message.value = message
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}