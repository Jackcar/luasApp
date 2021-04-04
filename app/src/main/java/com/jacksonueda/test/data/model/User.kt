package com.jacksonueda.test.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Id,
    val name: Name,
    val email: String,
    val picture: Picture
) : Parcelable

@Parcelize
data class Id(
    val name: String,
    val value: String
) : Parcelable

@Parcelize
data class Name(
    val title: String,
    val first: String,
    val last: String
) : Parcelable

@Parcelize
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Parcelable
