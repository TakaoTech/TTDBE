package com.takaotech.route.github.repository

import com.takaotech.route.github.model.GHRepository
import com.takaotech.route.github.model.GHUser
import io.ktor.util.logging.*
import org.kohsuke.github.GitHub
import org.koin.core.annotation.Single
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Single
class GithubRepository(
    private val logger: Logger,
    private val githubClient: GitHub
) {
    suspend fun getAllStarts() = suspendCoroutine {
        try {
            val stars = githubClient
                .myself
                .listStarredRepositories()
                .toList().mapIndexed { index, repository ->
                    logger.info("Processing repository $index, ID=${repository.id} Name=${repository.name} ")
                    GHRepository(
                        id = repository.id,
                        name = repository.name,
                        fullName = repository.fullName,
                        url = repository.url.toString(),
                        user = repository.owner.let { user ->
                            GHUser(
                                user.id,
                                user.login,
                                user.url.toString()
                            )
                        },
                        languages = mapOf()
                    )
                }
            it.resume(stars)
        } catch (ex: Throwable) {
            ex.printStackTrace()
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