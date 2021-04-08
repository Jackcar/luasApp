package com.jacksonueda.test.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(
    val id: Int,
    val nodeId: String,
    val name: String,
    val fullName: String,
    val private: Boolean,
    val owner: User,
    val htmlUrl: String,
    val description: String,
    val fork: Boolean,
    val url: String,
    val createdAt: String,
    val updatedAt: String,
    val pushedAt: String,
    val gitUrl: String,
    val sshUrl: String,
    val homepage: String,
    val size: Int,
    val stargazersCount: Int,
    val watchersCount: Int,
    val hasIssues: Boolean,
    val hasProjects: Boolean,
    val hasDownloads: Boolean,
    val forksCount: Int,
    val openIssuesCount: Int,
    val watchers: Int,
    val defaultBranch: String
) : Parcelable
