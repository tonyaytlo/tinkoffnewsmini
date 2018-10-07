package com.tony.tinkoffnewsmini.ext

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

class ObservableTransformerFactory {

    companion object {
        fun <T> applyAsync(
                subscribeScheduler: Scheduler = Schedulers.io(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()
        ): ObservableTransformer<T, T> {
            return ObservableTransformer { source ->
                source
                        .subscribeOn(subscribeScheduler)
                        .observeOn(observeScheduler)
            }
        }

        fun <T> applyAsyncSingle(
                subscribeScheduler: Scheduler = Schedulers.io(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()
        ): SingleTransformer<T, T> {
            return SingleTransformer { source ->
                source
                        .subscribeOn(subscribeScheduler)
                        .observeOn(observeScheduler)
            }
        }

        fun applyAsyncCompletable(
                subscribeScheduler: Scheduler = Schedulers.io(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()
        ) : CompletableTransformer {
            return CompletableTransformer {
                it
                        .subscribeOn(subscribeScheduler)
                        .observeOn(observeScheduler)
            }
        }

        fun <T> applyAsyncFlowable(
                subscribeScheduler: Scheduler = Schedulers.io(),
                observeScheduler: Scheduler = AndroidSchedulers.mainThread()
        ): FlowableTransformer<T, T> {
            return FlowableTransformer { source ->
                source
                        .subscribeOn(subscribeScheduler)
                        .observeOn(observeScheduler)
            }
        }
    }
}

//observable
fun <T> Observable<T>.applyObservableCompute(): Observable<T> {
    return compose(ObservableTransformerFactory.applyAsync(subscribeScheduler = Schedulers.computation()))
}

fun <T> Observable<T>.applyObservableAsync(scheduler: Scheduler = Schedulers.io()): Observable<T> {
    return compose(ObservableTransformerFactory.applyAsync(subscribeScheduler = scheduler))
}

fun <T> Observable<T>.applyAsync(subscribeScheduler: Scheduler, observeScheduler: Scheduler): Observable<T> {
    return compose(ObservableTransformerFactory.applyAsync(
            subscribeScheduler = subscribeScheduler,
            observeScheduler = observeScheduler
    ))
}

fun <T> Observable<T>.applyMainThread(): Observable<T> {
    return compose(ObservableTransformerFactory.applyAsync(
            AndroidSchedulers.mainThread(),
            AndroidSchedulers.mainThread()))
}

//single
fun <T> Single<T>.applyObservableCompute(): Single<T> {
    return compose(ObservableTransformerFactory.applyAsyncSingle(subscribeScheduler = Schedulers.computation()))
}

fun <T> Single<T>.applyObservableAsync(scheduler: Scheduler = Schedulers.io()): Single<T> {
    return compose(ObservableTransformerFactory.applyAsyncSingle(subscribeScheduler = scheduler))
}

fun <T> Single<T>.applyAsync(subscribeScheduler: Scheduler, observeScheduler: Scheduler): Single<T> {
    return compose(ObservableTransformerFactory.applyAsyncSingle(
            subscribeScheduler = subscribeScheduler,
            observeScheduler = observeScheduler
    ))
}

fun <T> Single<T>.applyMainThread(): Single<T> {
    return compose(ObservableTransformerFactory.applyAsyncSingle(
            AndroidSchedulers.mainThread(),
            AndroidSchedulers.mainThread()))
}

//completable
fun Completable.applyObservableCompute(): Completable {
    return compose(ObservableTransformerFactory.applyAsyncCompletable(subscribeScheduler = Schedulers.computation()))
}

fun Completable.applyObservableAsync(scheduler: Scheduler = Schedulers.io()): Completable {
    return compose(ObservableTransformerFactory.applyAsyncCompletable(subscribeScheduler = scheduler))
}

fun Completable.applyAsync(subscribeScheduler: Scheduler, observeScheduler: Scheduler): Completable {
    return compose(ObservableTransformerFactory.applyAsyncCompletable(
            subscribeScheduler = subscribeScheduler,
            observeScheduler = observeScheduler
    ))
}

fun Completable.applyMainThread(): Completable {
    return compose(ObservableTransformerFactory.applyAsyncCompletable(
            AndroidSchedulers.mainThread(),
            AndroidSchedulers.mainThread()))
}

//flowable
fun <T> Flowable<T>.applyObservableCompute(): Flowable<T> {
    return compose(ObservableTransformerFactory.applyAsyncFlowable(subscribeScheduler = Schedulers.computation()))
}

fun <T> Flowable<T>.applyObservableAsync(scheduler: Scheduler = Schedulers.io()): Flowable<T> {
    return compose(ObservableTransformerFactory.applyAsyncFlowable(subscribeScheduler = scheduler))
}

fun <T> Flowable<T>.applyAsync(subscribeScheduler: Scheduler, observeScheduler: Scheduler): Flowable<T> {
    return compose(ObservableTransformerFactory.applyAsyncFlowable(
            subscribeScheduler = subscribeScheduler,
            observeScheduler = observeScheduler
    ))
}

fun <T> Flowable<T>.applyMainThread(): Flowable<T> {
    return compose(ObservableTransformerFactory.applyAsyncFlowable(
            AndroidSchedulers.mainThread(),
            AndroidSchedulers.mainThread()))
}

//retry when
fun <T> Observable<T>.repeatRequest(interval: Long, timeUnit: TimeUnit = TimeUnit.SECONDS): Observable<T> {
    return retryWhen { it.flatMap { Observable.timer(interval, timeUnit) } }
}