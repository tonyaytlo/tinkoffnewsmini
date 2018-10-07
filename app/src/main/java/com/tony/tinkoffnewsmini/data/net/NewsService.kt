package ru.galt.app.domain.api

import com.tony.tinkoffnews.data.entity.NewsContentResponse
import com.tony.tinkoffnews.data.entity.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("news")
    fun getNewsEntityList(): Observable<NewsResponse>

    @GET("news_content")
    fun getNewsContentEntity(@Query("id") id: String): Observable<NewsContentResponse>

}