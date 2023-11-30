package com.takaotech.di

import org.jetbrains.exposed.sql.Database
import org.kohsuke.github.GitHubBuilder
import org.koin.dsl.module

val generalModule = module {
    single {
        GitHubBuilder().apply {
            withOAuthToken(System.getenv("GITHUB_TOKEN"))
        }.build()
    }

    single<Database> {
        Database.connect(System.getenv("DB_URL"), System.getenv("DB_DRIVER"))
    }
}