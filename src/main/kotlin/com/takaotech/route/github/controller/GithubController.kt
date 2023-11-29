package com.takaotech.route.github.controller

import com.takaotech.route.github.repository.GithubRepository

class GithubController(private val githubRepository: GithubRepository) {

    suspend fun getStars() {
        githubRepository.getAllStarts()
    }
}