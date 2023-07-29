package ru.student.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import ru.student.vknewsclient.ui.theme.MainScreen
import ru.student.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val contract = VK.getVKAuthActivityResultContract()
                val authState = viewModel.authState.observeAsState(initial = AuthState.Initial)
                val launcher = rememberLauncherForActivityResult(contract = contract, onResult = {
                    viewModel.performAuthResult(it)
                })


                when (authState.value) {
                    AuthState.Authorized -> {
                        MainScreen()
                    }

                    AuthState.Initial -> {

                    }

                    AuthState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(arrayListOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }
                }
            }
        }
    }
}
