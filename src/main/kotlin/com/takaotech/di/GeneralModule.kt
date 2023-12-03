package com.takaotech.di

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
}