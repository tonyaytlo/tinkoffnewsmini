package com.tony.tinkoffnewsmini.domain.command

import com.tony.tinkoffnews.data.entity.NewsItem
import io.reactivex.Observable
import ru.galt.app.domain.base.ApiCommand
import javax.inject.Inject
//Repository abs
class GetNewsCommand @Inject constructor(
        private val remote: GetNewsRemoteCommand,
        private val cache: GetNewsCacheCommand
) : ApiCommand<List<NewsItem>>() {

    var fetch: Boolean = false


    override fun serviceRequest(): Observable<List<NewsItem>> {
        if (fetch || !cache.isCached()) {
            return remote.execute()
        }
        return cache.execute()
    }
}