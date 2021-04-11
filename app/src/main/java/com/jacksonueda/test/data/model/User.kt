package com.jacksonueda.test.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String = "",
    val id: Int = 0,
    val nodeId: String = "",
    val avatarUrl: String = "",
    val gravatarUrl: String = "",
    val url: String = "",
    val type: String = "",
    val siteAdmin: Boolean = false
) : Parcelable