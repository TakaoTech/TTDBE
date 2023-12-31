package com.takaotech.plugins

import com.takaotech.route.github.data.GithubDepositoryTable
import com.takaotech.route.github.data.GithubUserTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.get

fun Application.initExposed() {
    val database = get<Database>()

    transaction(database) {
        SchemaUtils.create(GithubDepositoryTable, GithubUserTable)
    }
}