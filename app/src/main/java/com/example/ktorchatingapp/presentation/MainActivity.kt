package com.example.ktorchatingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.example.ktorchatingapp.presentation.chat.ChatScreen
import com.example.ktorchatingapp.presentation.login.LoginScreen
import com.example.ktorchatingapp.presentation.theme.KtorChatingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorChatingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login_screen" ){
                        composable(route = "login_screen"){
                            LoginScreen(onNavigate = navController::navigate)
                        }

                        composable(route = "chat_screen/{username}", arguments = listOf(
                            navArgument(name = "username"){
                                type = NavType.StringType
                                nullable = true
                            }
                        )){
                            ChatScreen(username = it.arguments?.getString("username"),)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KtorChatingAppTheme {

    }
}