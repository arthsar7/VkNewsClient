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
import ru.student.vknewsclient.presentation.FeedApplication
import ru.student.vknewsclient.presentation.ViewModelFactory
import ru.student.vknewsclient.ui.theme.MainScreen
import ru.student.vknewsclient.ui.theme.VkNewsClientTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    private val component by lazy {
        (application as FeedApplication).component
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)

                val contract = VK.getVKAuthActivityResultContract()
                val authState = viewModel.authState.collectAsState(initial = AuthState.Initial)

                val launcher = rememberLauncherForActivityResult(contract = contract) {
                    viewModel.performAuthResult()
                }

                when (authState.value) {

                    AuthState.Authorized -> {
                        MainScreen(viewModelFactory)
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
