package ru.student.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class FeedResponseDto(
    @SerializedName("response") val feedContent: FeedContentDto
)