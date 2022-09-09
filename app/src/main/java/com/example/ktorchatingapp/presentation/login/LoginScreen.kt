package com.example.ktorchatingapp.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ktorchatingapp.presentation.login.ui.MyEditText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.onJoin.collectLatest { username ->
            onNavigate("chat_screen/$username")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            MyEditText(
                modifier = Modifier.fillMaxWidth(),
                hint = "Username ...",
                value = viewModel.username.value,
                change = viewModel::onUsernameChange
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = viewModel::onJoinChatClick) {
                Text(text = "login")
            }
        }
    }

}