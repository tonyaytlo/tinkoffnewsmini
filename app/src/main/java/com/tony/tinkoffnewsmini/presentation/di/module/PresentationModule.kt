package ru.galt.app.presentation.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideAppContext() = this.application

    @Provides
    @Singleton
    internal fun provideContext(): Context = this.application

    @Provides
    @Singleton
    internal fun provideContentResolver() = this.application.contentResolver

    @Provides
    @Singleton
    internal fun provideResources(): Resources = this.application.resources
}