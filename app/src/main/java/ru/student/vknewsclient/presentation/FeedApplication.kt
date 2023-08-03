package ru.student.vknewsclient.presentation

import android.app.Application
import ru.student.vknewsclient.di.ApplicationComponent
import ru.student.vknewsclient.di.DaggerApplicationComponent

class FeedApplication: Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}