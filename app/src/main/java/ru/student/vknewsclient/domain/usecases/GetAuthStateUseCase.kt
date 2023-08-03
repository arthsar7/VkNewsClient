package ru.student.vknewsclient.domain.usecases

import ru.student.vknewsclient.domain.repository.FeedRepository
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    operator fun invoke() = repository.getAuthStateFlow()
}