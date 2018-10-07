package com.tony.tinkoffnewsmini

import android.app.Application
import ru.galt.app.presentation.di.AppComponent
import ru.galt.app.presentation.di.DaggerAppComponent
import ru.galt.app.presentation.di.PresentationModule

class NewsApp : Application() {

    var appComponent: AppComponent? = null

    companion object {
        lateinit var instance: NewsApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        getCustomAppComponent()!!
                .appComponent()
                .build()
                .inject(this)
    }

    fun getCustomAppComponent(): AppComponent? {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .presentationModule(PresentationModule(this))
                    .build()
        }
        return appComponent
    }
}