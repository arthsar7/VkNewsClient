package ru.student.vknewsclient.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.student.vknewsclient.di.ApplicationComponent
import ru.student.vknewsclient.di.DaggerApplicationComponent

class FeedApplication: Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as FeedApplication).component
}