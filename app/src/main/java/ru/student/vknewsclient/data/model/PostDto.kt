package ru.student.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Long,
    @SerializedName("source_id") val sourceId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("views") val viewsDto: ViewsDto,
    @SerializedName("reposts") val repostsDto: RepostsDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?
)
