package com.jacksonueda.luastest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val url: String,
    val origin: Origin,
    val location: Location,
    val episode: List<String>
) : Parcelable
