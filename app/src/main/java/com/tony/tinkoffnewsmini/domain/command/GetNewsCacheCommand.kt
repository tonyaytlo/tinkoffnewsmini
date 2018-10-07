package com.tony.tinkoffnewsmini.domain.command

import com.tony.tinkoffnews.data.entity.NewsItem
import com.tony.tinkoffnews.data.repository.datasource.NewsCache
import ru.galt.app.domain.base.ApiCommand
import javax.inject.Inject

class GetNewsCacheCommand
@Inject constructor(
        private val cache: NewsCache
) : ApiCommand<List<NewsItem>>() {

    override fun serviceRequest() = cache.get()

    fun isCached() = cache.isCached()
}