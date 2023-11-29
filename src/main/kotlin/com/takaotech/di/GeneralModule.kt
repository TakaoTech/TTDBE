package com.takaotech.di

import org.kohsuke.github.GitHubBuilder
import org.koin.dsl.module

val generalModule = module {
    single {
        GitHubBuilder().apply {
            withOAuthToken(System.getenv("GITHUB_TOKEN"))
        }.build()
    }
}