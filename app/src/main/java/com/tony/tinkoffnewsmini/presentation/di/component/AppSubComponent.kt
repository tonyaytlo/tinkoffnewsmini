package ru.galt.app.presentation.di

import com.tony.tinkoffnewsmini.NewsApp
import dagger.Subcomponent

@Subcomponent
interface AppSubComponent {
    fun inject(app: NewsApp)

    @Subcomponent.Builder
    interface Builder {
        fun build(): AppSubComponent
    }
}