package com.example.jetnews.data.posts

import com.example.jetnews.data.interests.RandomUserResponse
import com.example.jetnews.data.interests.RandomUserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"

val contentType = "application/json".toMediaType()

val json = Json {
    ignoreUnknownKeys = true // Ignore unknown keys
}

@Serializable
data class FakeNewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

@Serializable
data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

@Serializable
data class Source(
    val id: String?,
    val name: String
)

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory(contentType))
    .baseUrl(BASE_URL)
    .build()

interface FakeNewsService {
    @GET("top-headlines")
    suspend fun getFakeNews(@Query("pageSize") numberOfResults: Int, @Query("page") page: Int, @Query("country") country: String, @Query("apiKey") apiKye: String): FakeNewsResponse
}

object FakeNewsApi {
    val service: FakeNewsService by lazy {
        retrofit.create(FakeNewsService::class.java)
    }
}