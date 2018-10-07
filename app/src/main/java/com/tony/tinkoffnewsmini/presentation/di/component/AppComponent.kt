package ru.galt.app.presentation.di


import dagger.Component
import ru.galt.app.domain.di.DomainModule
import ru.galt.app.presentation.screens.subscriptions.di.NewsComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DomainModule::class,
    PresentationModule::class
])
interface AppComponent {

    fun appComponent(): AppSubComponent.Builder

    fun newsComponent(): NewsComponent.Builder

}