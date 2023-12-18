package com.takaotech.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject
import kotlin.time.Duration.Companion.days

fun Application.configureAuth() {
    val userTable by inject<UserHashedTableAuth>()

    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAge = 30.days
        }
    }

    authentication {
        basic("takao-auth-basic") {
            realm = "TTD Admin"
            validate { credentials ->
                userTable.authenticate(credentials)
            }
        }

        session<UserSession>("takao-auth-session") {
            validate { session ->
                if (session.name.startsWith("takao-session")) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respondRedirect("/login")
            }
        }

//        form(name = "takao-auth-form") {
//            userParamName = "username"
//            passwordParamName = "password"
//            validate {
//                userTable.authenticate(it)
//            }
//            challenge {credentials ->
//                if (credentials != null) {
//                    userTable.authenticate(credentials)
//                }
//            }
//        }
    }
}

data class UserSession(val name: String) : Principal
