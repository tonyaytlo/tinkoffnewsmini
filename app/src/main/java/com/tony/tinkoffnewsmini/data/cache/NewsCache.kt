package com.tony.tinkoffnews.data.repository.datasource

import android.content.Context
import com.tony.tinkoffnews.data.cache.FileManager
import com.tony.tinkoffnews.data.cache.serializer.Serializer
import com.tony.tinkoffnews.data.entity.NewsItem
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsCache
@Inject constructor(context: Context,
                    private val serializer: Serializer,
                    private val fileManager: FileManager) {

    private val DEFAULT_FILE_NAME = "db_file"
    private val cacheDir: File = context.cacheDir
    private val dbFile: File by lazy { buildFile() }


    fun isCached() = fileManager.exists(buildFile())

    fun put(data: List<NewsItem>) {
        if (isCached()) {
            clear()
        }
        val jsonString = this.serializer.serialize(data)
        Observable.fromCallable {
            fileManager.writeToFile(dbFile, jsonString)
        }.subscribe { }
    }

    fun get(): Observable<List<NewsItem>> =
            Observable.create<List<NewsItem>> { emitter ->
                val fileContent = fileManager.readFileContent(dbFile)
                val newsEntity = serializer.deserializeList(fileContent, NewsItem::class.java)

                if (newsEntity != null) {
                    emitter.onNext(newsEntity)
                    emitter.onComplete()
                } else {
                    emitter.onError(IllegalArgumentException()) //mb need custom error impl
                }
            }

    fun clear() {
        fileManager.clearDirectory(dbFile)
    }

    private fun buildFile(): File {
        val fileNameBuilder = StringBuilder()
        fileNameBuilder.append(this.cacheDir.path)
        fileNameBuilder.append(File.separator)
        fileNameBuilder.append(DEFAULT_FILE_NAME)

        return File(fileNameBuilder.toString())
    }
}