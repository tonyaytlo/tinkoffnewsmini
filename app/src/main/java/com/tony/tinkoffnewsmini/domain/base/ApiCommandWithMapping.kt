package ru.galt.app.domain.base

import com.tony.tinkoffnewsmini.R
import com.tony.tinkoffnewsmini.ext.applyObservableAsync
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.UnknownHostException

abstract class ApiCommandWithMapping<RESULT, RESPONSE> {

    protected abstract fun serviceRequest(): Observable<RESPONSE>
    private var subscribeScheduler = Schedulers.io()

    fun execute(): Observable<RESULT> {
        val request = serviceRequest()
                .applyObservableAsync(subscribeScheduler)
                .onErrorResumeNext(Function<Throwable, Observable<RESPONSE>> {
                    if (it is HttpException) {
                        Observable.error(BaseException(""))
                    } else if (it is ConnectException || it is UnknownHostException) {
                        Observable.error(BaseException(R.string.error_no_connection))
                    } else {
                        Observable.error(it)
                    }
                })
                .map {
                    if (it is Response<*>) {
                        val c = it.code()
                        if (c != HttpURLConnection.HTTP_OK &&
                                c != HttpURLConnection.HTTP_CREATED &&
                                c != HttpURLConnection.HTTP_ACCEPTED &&
                                c != HttpURLConnection.HTTP_NO_CONTENT) {
                            throw BaseException("")
                        }
                    }
                    it
                }
        return returnOnUI(
                afterResponse(
                        request
                                .observeOn(Schedulers.computation())
                )
        )
    }

    private fun returnOnUI(result: Observable<RESULT>): Observable<RESULT> =
            result.observeOn(AndroidSchedulers.mainThread())

    abstract fun afterResponse(response: Observable<RESPONSE>): Observable<RESULT>

    fun setSubscribeScheduler(scheduler: Scheduler) {
        subscribeScheduler = scheduler
    }
}