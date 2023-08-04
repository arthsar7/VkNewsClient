package ru.student.vknewsclient.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.domain.usecases.ChangeFaveStatusUseCase
import ru.student.vknewsclient.domain.usecases.GetFavoritesUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoritesUseCase,
    private val changeFaveStatusUseCase: ChangeFaveStatusUseCase
): ViewModel() {
    private val favoriteFlow = getFavoritesUseCase()
    val screenState = favoriteFlow
        .map {
            if(it.isEmpty()) FavoriteScreenState.Empty
            else FavoriteScreenState.Posts(it)
        }
        .onStart { emit(FavoriteScreenState.Loading) }

    fun changeFaveStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            changeFaveStatusUseCase(feedPost)
        }
    }
}