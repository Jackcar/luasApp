package com.jacksonueda.test.data.model

import com.google.gson.annotations.SerializedName

data class PagedResponse<T>(
    @SerializedName("info") val pageInfo: PageInfo,
    val results: List<T> = listOf()
)

data class PageInfo(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)