package ru.student.vknewsclient.domain.usecases

import ru.student.vknewsclient.domain.repository.FeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke() {
        repository.loadNextData()
    }
}