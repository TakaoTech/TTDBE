package com.takaotech.di

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.util.logging.*
import org.jetbrains.exposed.sql.Database
import org.kohsuke.github.GitHubBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

fun getGeneralModule(log: Logger): Module = module {
    single<Logger> {
        log
    }

    single {
        GitHubBuilder().apply {
            withOAuthToken(System.getenv("GITHUB_TOKEN"))
        }.build()
    }

    single<Database> {
        Database.connect(System.getenv("DB_URL"), System.getenv("DB_DRIVER"))
    }

    single<UserHashedTableAuth> {
        val digestFunction = getDigestFunction("SHA-256") { System.getenv("auth.digest") + it.length }

        UserHashedTableAuth(
            table = mapOf(
                System.getenv("auth.username") to digestFunction(System.getenv("auth.password")),
            ),
            digester = digestFunction
        )
    }
}