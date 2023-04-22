package com.gmacv.moviedemo.di

import com.gmacv.moviedemo.data.api.Apis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Apis = retrofit.create(Apis::class.java)

//    @Provides
//    @Singleton
//    fun provideOkHttpClient() : OkHttpClient {
//        val builder = OkHttpClient.Builder()
//        return builder
//            .addInterceptor(HttpLoggingInterceptor())
//            .addInterceptor { chain ->
//                val newRequest = chain.request().newBuilder()
//                    .addHeader(
//                        "Authorization:Bearer",
//                        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzAyM2M0YjdkYTE5ODUxMjk1OGM5YzU1OGM1ZjQzMSIsInN1YiI6IjY0NDIyZTY1YjNmNmY1MDU1YTlkY2RkNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xiYfSLgAsQ5B4fMNyDo2KGNhDclO3BFx3Kr0sLr_R00"
//                    )
//                    .build()
//                chain.proceed(newRequest)
//            }
//            .build()
//    }

}