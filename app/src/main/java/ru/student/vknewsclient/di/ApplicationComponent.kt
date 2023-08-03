package ru.student.vknewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.student.vknewsclient.presentation.main.MainActivity

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun getCommentScreenComponentFactory(): CommentsScreenComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}