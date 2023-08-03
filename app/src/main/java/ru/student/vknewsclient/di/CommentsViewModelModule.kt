package ru.student.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.student.vknewsclient.presentation.comments.CommentsViewModel
@Suppress("UNUSED")
@Module
interface CommentsViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(CommentsViewModel::class)
    fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel
}