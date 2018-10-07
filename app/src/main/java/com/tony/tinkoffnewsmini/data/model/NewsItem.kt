package com.tony.tinkoffnews.data.entity


data class NewsResponse constructor(val payload: List<NewsItem>)

data class NewsItem constructor(val id: String, val name: String, val text: String,
                                val publicationDate: PublicationDate)

data class PublicationDate constructor(val milliseconds: Long)
