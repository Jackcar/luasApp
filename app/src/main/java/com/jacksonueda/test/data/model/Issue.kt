package com.jacksonueda.test.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Issue(
    val url: String = "",
    val repositoryUrl: String = "",
    val id: Int = 0,
    val nodeId: String = "",
    val number: Int = 0,
    val title: String = "",
    val user: User,
    val state: String = "",
    val locked: Boolean = false,
    val comments: Int = 0,
    val createdAt: String,
    val updatedAt: String = "",
    val closedAt: String = ""
) : Parcelable
