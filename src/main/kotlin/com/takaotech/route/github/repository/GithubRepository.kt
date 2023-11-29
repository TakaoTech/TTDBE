package com.takaotech.route.github.repository

import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import org.koin.core.annotation.Single
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Single
class GithubRepository(private val githubClient: GitHub) {
    suspend fun getAllStarts() = suspendCoroutine<List<GHRepository>> {
        try {
            val stars = githubClient
                .myself
                .listStarredRepositories()
                .toList()
            it.resume(stars)
        } catch (ex: IOException) {
            it.resumeWithException(ex)
        }
    }

    suspend fun getLanguagesByRepository(repositoryId: Long) = suspendCoroutine<Map<String, Long>> {
        try {
            val listLanguages = githubClient
                .getRepositoryById(repositoryId)
                .listLanguages()
            it.resume(listLanguages)
        } catch (ex: IOException) {
            it.resumeWithException(ex)
        }
    }
}