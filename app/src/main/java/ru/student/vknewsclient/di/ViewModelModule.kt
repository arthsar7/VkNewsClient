package ru.student.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.student.vknewsclient.presentation.favorite.FavoriteViewModel
import ru.student.vknewsclient.presentation.main.MainViewModel
import ru.student.vknewsclient.presentation.news.FeedViewModel

@Module
@Suppress("UNUSED")
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel


    @IntoMap
    @Binds
    @ViewModelKey(FeedViewModel::class)
    fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(FavoriteViewModel::class)
    fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

}