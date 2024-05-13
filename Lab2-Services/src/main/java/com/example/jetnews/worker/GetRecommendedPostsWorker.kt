package com.example.jetnews.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.jetnews.data.posts.FakeNewsApi
import com.example.jetnews.data.posts.impl.FakePostsRepository
import com.example.jetnews.model.mapArticlesToPosts

class GetRecommendedPostsWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val response = FakeNewsApi.service.getFakeNews(10, 1, "co", "3f10a8d63a5c4d058f6848d8e0e1d41c")
            Log.i("API_CALL", "Got values")
            Log.i("API_CALL", response.articles.toString())
            val newRecommendedPosts = mapArticlesToPosts(response.articles)
            Log.i("API_CALL", "Mapped values")
            FakePostsRepository.saveRecommended(newRecommendedPosts)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}