package com.tony.tinkoffnewsmini.domain.command

import com.tony.tinkoffnews.data.entity.NewsContent
import com.tony.tinkoffnews.data.entity.NewsContentResponse
import io.reactivex.Observable
import ru.galt.app.domain.api.NewsService
import ru.galt.app.domain.base.ApiCommandWithMapping
import javax.inject.Inject

class GetContentCommand
@Inject constructor(private val remote: NewsService) : ApiCommandWithMapping<NewsContent, NewsContentResponse>() {

    var id = ""

    override fun serviceRequest() = remote.getNewsContentEntity(id)
    override fun afterResponse(response: Observable<NewsContentResponse>) = response.map {
        it.payload
    }

}