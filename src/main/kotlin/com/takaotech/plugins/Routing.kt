package com.takaotech.plugins

import com.takaotech.route.github.githubRoute
import com.takaotech.route.github.repository.GithubRepository
import com.takaotech.utils.serializeToJson
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(Resources)
    val githubRepository by inject<GithubRepository>()
    routing {


        get("/") {
            val startList = githubRepository.getAllStarts()

            call.respondText(
                startList
                    .first()
                    .serializeToJson()
            )
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
        get<Articles> { article ->
            // Get all articles ...
            call.respond("List of articles sorted starting from ${article.sort}")
        }
    }
    githubRoute()
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
