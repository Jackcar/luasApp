package com.jacksonueda.test.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(
    val id: Int = 0,
    val nodeId: String = "",
    val name: String = "",
    val fullName: String = "",
    val private: Boolean = false,
    val owner: User,
    val htmlUrl: String = "",
    val description: String = "",
    val fork: Boolean = false,
    val url: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val pushedAt: String = "",
    val gitUrl: String = "",
    val sshUrl: String = "",
    val homepage: String = "",
    val size: Int = 0,
    val stargazersCount: Int = 0,
    val watchersCount: Int = 0,
    val language: String = "",
    val hasIssues: Boolean = false,
    val hasProjects: Boolean = false,
    val hasDownloads: Boolean = false,
    val forksCount: Int = 0,
    val openIssuesCount: Int = 0,
    val watchers: Int = 0,
    val defaultBranch: String = ""
) : Parcelable
