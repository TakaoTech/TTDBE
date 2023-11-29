package com.takaotech.route.github

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.githubRoute() {
    routing {
        get<GithubRoute>() {

        }
    }
}

@Serializable
@Resource("/github")
class GithubRoute()