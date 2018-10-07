package com.tony.tinkoffnews.data.entity

data class NewsContentResponse constructor(val payload: NewsContent)

data class NewsContent constructor(val title: Title, val content: String)

data class Title constructor(val id: String, val name: String, val text: String,
                             val publicationDate: PublicationDate)
