package ru.student.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass
@Suppress("UNUSED")
@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val type: KClass<out ViewModel>)
