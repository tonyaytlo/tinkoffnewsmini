package ru.galt.app.domain.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.galt.app.domain.api.NewsService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


private const val WRITE_TIMEOUT = 20L
private const val READ_TIMEOUT = 20L
private const val BASE_URL = " https://api.tinkoff.ru/v1/"
private const val PREFERENCES_NAME = "tony.tinkoffnews.com"

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .create()
    }

    @Provides
    @Singleton
    fun provideInquiriesService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): HttpUrl? = HttpUrl.parse(BASE_URL)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverter(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAdapter() = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(builder: Retrofit.Builder,
                        okHttpClient: OkHttpClient,
                        gsonConverterFactory: GsonConverterFactory,
                        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                        url: HttpUrl?): Retrofit {
        return builder
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .baseUrl(url)
                .build()
    }
}