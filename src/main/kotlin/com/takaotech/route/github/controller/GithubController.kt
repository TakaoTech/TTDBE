package com.takaotech.route.github.controller

import com.takaotech.route.github.repository.GithubRepository
import org.koin.core.annotation.Factory

@Factory
class GithubController(private val githubRepository: GithubRepository) {

    suspend fun getStarsFromZeroAndStore() {
        githubRepository.getAllStarts().map {
            it.copy(
                languages = githubRepository.getLanguagesByRepository(it.id)
            )
        }
    }
}