package ru.student.vknewsclient.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import ru.student.vknewsclient.presentation.main.AuthState

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token?.isValid ?: false
        _authState.value = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        when (result) {
            is VKAuthenticationResult.Failed -> {
                _authState.value = AuthState.NotAuthorized
            }

            is VKAuthenticationResult.Success -> {
                _authState.value = AuthState.Authorized
            }
        }
    }
}