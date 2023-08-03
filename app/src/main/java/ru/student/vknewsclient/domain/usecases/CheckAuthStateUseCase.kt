package ru.student.vknewsclient.domain.usecases

import ru.student.vknewsclient.domain.repository.FeedRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}