package ru.student.vknewsclient.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.presentation.ViewModelFactory

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {
    fun getViewModelFactory(): ViewModelFactory
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}