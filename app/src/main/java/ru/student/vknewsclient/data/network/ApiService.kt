package ru.student.vknewsclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.student.vknewsclient.data.model.CommentsResponseDto
import ru.student.vknewsclient.data.model.FaveResponseDto
import ru.student.vknewsclient.data.model.FeedResponseDto
import ru.student.vknewsclient.data.model.LikeResponseDto

interface ApiService {
    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendations(
        @Query("access_token") token: String
    ): FeedResponseDto

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendations(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): FeedResponseDto

    @GET("newsfeed.ignoreItem?v=5.131&type=wall")
    suspend fun ignorePost(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    )


    @GET("likes.add?v=5.131&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikeResponseDto

    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikeResponseDto

    @GET("fave.addPost?v=5.131")
    suspend fun addFave(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("id") postId: Long
    )

    @GET("fave.removePost?v=5.131")
    suspend fun removeFave(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("id") postId: Long
    )

    @GET("fave.get?v=5.131&extended=1&item_type=post")
    suspend fun loadFaves(
        @Query("access_token") token: String
    ): FaveResponseDto

    @GET("wall.getComments?v=5.131&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long
    ): CommentsResponseDto


}