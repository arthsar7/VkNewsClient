package ru.student.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import ru.student.vknewsclient.domain.entity.AuthState
import ru.student.vknewsclient.presentation.getApplicationComponent
import ru.student.vknewsclient.ui.theme.MainScreen
import ru.student.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())

            val contract = VK.getVKAuthActivityResultContract()
            val authState = viewModel.authState.collectAsState(initial = AuthState.Initial)

            val launcher = rememberLauncherForActivityResult(contract = contract) {
                viewModel.performAuthResult()
            }

            VkNewsClientTheme {
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
