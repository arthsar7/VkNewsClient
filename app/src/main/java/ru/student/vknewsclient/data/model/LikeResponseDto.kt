package ru.student.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikeResponseDto(
    @SerializedName("response") val likes: LikesCountDto
)
