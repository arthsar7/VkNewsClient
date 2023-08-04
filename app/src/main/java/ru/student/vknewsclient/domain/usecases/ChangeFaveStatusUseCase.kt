package ru.student.vknewsclient.domain.usecases

import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.domain.repository.FeedRepository
import javax.inject.Inject

class ChangeFaveStatusUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        repository.changeFaveStatus(feedPost)
    }
}