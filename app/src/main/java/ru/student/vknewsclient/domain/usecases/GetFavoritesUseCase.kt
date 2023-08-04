package ru.student.vknewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.domain.repository.FeedRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    operator fun invoke(): StateFlow<List<FeedPost>>{
       return repository.getFavorites()
    }
}