package ru.student.vknewsclient.di

import android.content.Context
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.student.vknewsclient.data.network.ApiFactory
import ru.student.vknewsclient.data.network.ApiService
import ru.student.vknewsclient.data.repository.FeedRepositoryImpl
import ru.student.vknewsclient.domain.repository.FeedRepository
@Suppress("UNUSED")
@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindRepository(repository: FeedRepositoryImpl): FeedRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
        @ApplicationScope
        @Provides
        fun provideStorage(context: Context): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }
}