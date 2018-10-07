package ru.galt.app.domain.base

import android.view.View
import io.reactivex.observers.DisposableSingleObserver
import ru.galt.app.extensions.setVisibility
import java.lang.ref.WeakReference

class BaseSingle<T> : DisposableSingleObserver<T>() {


    var onError: (Throwable) -> Unit = { }
    var onSuccess: (T) -> Unit = { }
    var onStart: () -> Unit = { }
    var onTerminate: () -> Unit = { }
    var loadingView: WeakReference<View>? = null

    override fun onStart() {
        loadingView?.get()?.setVisibility(true)
        onStart.invoke()
    }

    override fun onSuccess(t: T) {
        loadingView?.get()?.setVisibility(false)
        onTerminate.invoke()
        onSuccess.invoke(t)

    }

    override fun onError(e: Throwable) {
        loadingView?.get()?.setVisibility(false)
        onTerminate.invoke()
        onError.invoke(e)
    }

}