package com.example.jetnews.data.interests

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class RandomUserResponse(
    val results: List<Result>
)

@Serializable
data class Result(
    val name: Name
)

@Serializable
data class Name(
    val title: String,
    val first: String,
    val last: String
)

private const val BASE_URL = "https://randomuser.me/"

val contentType = "application/json".toMediaType()

val json = Json {
    ignoreUnknownKeys = true // Ignore unknown keys
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory(contentType))
    .baseUrl(BASE_URL)
    .build()

interface RandomUserService {
    @GET("api")
    suspend fun getRandomUsers(@Query("results") numberOfResults: Int): RandomUserResponse
}

object RandomUserApi {
    val service: RandomUserService by lazy {
        retrofit.create(RandomUserService::class.java)
    }
}