package ru.student.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class FaveResponseDto(
    @SerializedName("response") val faves: FaveContentDto
)