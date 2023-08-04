package ru.student.vknewsclient.data.model

import com.google.gson.annotations.SerializedName

data class FaveContentDto(
    @SerializedName("items") val posts: List<FavePostDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
)
