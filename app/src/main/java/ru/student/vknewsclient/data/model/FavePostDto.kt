package ru.student.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class FavePostDto(
    @SerializedName("post") val post: FavePostContentDto
)
