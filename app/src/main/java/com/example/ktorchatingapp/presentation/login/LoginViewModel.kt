package com.example.ktorchatingapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String>
        get() = _username

    private val _onJoin = MutableSharedFlow<String>()
    val onJoin :SharedFlow<String>
        get() = _onJoin.asSharedFlow()

    fun onUsernameChange(username:String){
        _username.value = username
    }

    fun onJoinChatClick() {
        viewModelScope.launch {
            if (_username.value.isNotBlank()){
                _onJoin.emit(_username.value)
            }
        }
    }
}