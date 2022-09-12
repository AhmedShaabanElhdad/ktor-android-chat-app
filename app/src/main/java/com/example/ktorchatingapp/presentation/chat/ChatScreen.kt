package com.example.ktorchatingapp.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ktorchatingapp.domain.model.Message
import com.example.ktorchatingapp.presentation.login.ui.MyEditText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    username: String?,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val state = viewModel.state.value


    LaunchedEffect(key1 = true) {
        viewModel.toast.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    DisposableEffect(key1 = lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.joinToChat()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
            items(state.data) { item: Message ->
                val yourMessage = item.username == username
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = if (yourMessage)
                        Alignment.CenterStart
                    else
                        Alignment.CenterEnd
                ) {
                    Column(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(30.dp)
                            .background(
                                color = if (yourMessage) Color.DarkGray else Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text(
                            text = item.username,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(text = item.message, color = Color.White)
                        Text(
                            text = item.date,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            MyEditText(
                modifier = Modifier.weight(1f),
                hint = "enter message here",
                value = viewModel.message.value,
                change = viewModel::onMessageChange
            )
            IconButton(onClick = viewModel::send) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send message")
            }
        }
    }


}