package ru.student.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class FavePostContentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("owner_id") val sourceId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?,
    @SerializedName("is_favorite") val isFavorite: Boolean
)
