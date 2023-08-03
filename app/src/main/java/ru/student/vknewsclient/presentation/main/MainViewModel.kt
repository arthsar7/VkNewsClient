package ru.student.vknewsclient.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.student.vknewsclient.domain.usecases.CheckAuthStateUseCase
import ru.student.vknewsclient.domain.usecases.GetAuthStateUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    val authState = getAuthStateUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}