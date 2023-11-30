package com.takaotech.route.github.data

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object GithubUser : Table() {
    val id: Column<Long> = long("id")
    val name: Column<String> = text("name")
    val url: Column<String> = text("url")

    override val primaryKey = PrimaryKey(id)
}