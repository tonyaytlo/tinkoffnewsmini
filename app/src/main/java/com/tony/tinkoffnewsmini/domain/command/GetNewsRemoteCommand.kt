package com.tony.tinkoffnewsmini.domain.command

import com.tony.tinkoffnews.data.entity.NewsItem
import com.tony.tinkoffnews.data.entity.NewsResponse
import com.tony.tinkoffnews.data.repository.datasource.NewsCache
import io.reactivex.Observable
import ru.galt.app.domain.api.NewsService
import ru.galt.app.domain.base.ApiCommandWithMapping
import javax.inject.Inject

class GetNewsRemoteCommand
@Inject constructor(
        private val cache: NewsCache,
        private val service: NewsService
) : ApiCommandWithMapping<List<NewsItem>, NewsResponse>() {

    override fun afterResponse(response: Observable<NewsResponse>): Observable<List<NewsItem>> {
        return response
                .map { it.payload.sortedByDescending { it.publicationDate.milliseconds } }
                .doOnNext { cache.put(it) }
    }

    override fun serviceRequest() = service.getNewsEntityList()
}