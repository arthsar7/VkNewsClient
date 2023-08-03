package ru.student.vknewsclient.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.student.vknewsclient.data.extensions.mergeWith
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.domain.usecases.ChangeLikeStatusUseCase
import ru.student.vknewsclient.domain.usecases.GetRecommendationsUseCase
import ru.student.vknewsclient.domain.usecases.IgnorePostUseCase
import ru.student.vknewsclient.domain.usecases.LoadNextDataUseCase
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val ignorePostUseCase: IgnorePostUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
    }

    private val recommendationsFlow = getRecommendationsUseCase()

    private val loadNextDataFlow = MutableSharedFlow<FeedScreenState>()

    @Suppress("USELESS_CAST")
    val screenState = recommendationsFlow
        .filter { it.isNotEmpty()}
        .map { FeedScreenState.Posts(it) as FeedScreenState }
        .onStart { emit(FeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch(exceptionHandler) {
            loadNextDataFlow.emit(
                FeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }


    fun removePost(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            ignorePostUseCase(feedPost)
        }
    }

}
