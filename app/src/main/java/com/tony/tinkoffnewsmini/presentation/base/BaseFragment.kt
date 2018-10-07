package ru.galt.app.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tony.tinkoffnewsmini.NewsApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.galt.app.presentation.di.AppComponent

abstract class BaseFragment : Fragment() {

    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(getAppComponent().getCustomAppComponent()!!)
    }

    private fun getAppComponent(): NewsApp {
        return NewsApp.instance
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    abstract fun inject(appComponent: AppComponent)

    abstract fun getLayoutId(): Int

    override fun onDestroy() {
        unsubscribe()
        super.onDestroy()
    }

    override fun onDestroyView() {
        unsubscribe()
        super.onDestroyView()
    }

    private fun unsubscribe() {
        disposable.clear()
    }

    fun addSubscription(d: Disposable) {
        disposable.add(d)
    }
}