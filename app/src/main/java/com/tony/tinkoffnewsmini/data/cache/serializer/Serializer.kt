package com.tony.tinkoffnews.data.cache.serializer

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Serializer @Inject constructor() {

    private val gson by lazy { Gson() }

    fun serialize(`object`: Any): String = gson.toJson(`object`)


    fun <T> deserialize(string: String, clazz: Class<T>): T? = gson.fromJson(string, clazz)


    fun <T> deserializeList(string: String, clazz: Class<T>): List<T>? {
        val typeOfT = TypeToken.getParameterized(List::class.java, clazz).type
        return gson.fromJson(string, typeOfT)
    }

}

