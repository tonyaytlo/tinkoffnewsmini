package ru.galt.app.presentation.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tony.tinkoffnewsmini.NewsApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.galt.app.presentation.di.AppComponent


abstract class BaseActivity : AppCompatActivity() {

    private var disposable = CompositeDisposable()


    abstract fun inject(appComponent: AppComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(getAppComponent().getCustomAppComponent()!!)
    }

    private fun getAppComponent(): NewsApp {
        return NewsApp.instance
    }


    override fun onDestroy() {
        super.onDestroy()
        unsubscribe()
    }


    private fun unsubscribe() {
        disposable.clear()
    }

    fun addSubscription(d: Disposable) {
        disposable.add(d)
    }
}