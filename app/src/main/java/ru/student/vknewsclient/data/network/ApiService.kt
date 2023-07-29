package ru.student.vknewsclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.student.vknewsclient.data.model.FeedResponseDto

interface ApiService {
    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendation(
        @Query("access_token") token: String
    ) : FeedResponseDto

}