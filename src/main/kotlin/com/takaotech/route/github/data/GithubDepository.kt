package com.takaotech.route.github.data

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.json.json

object GithubDepository : Table() {
    /**
     * GitHub Repository Id
     */
    val id: Column<Long> = long("id")

    val name: Column<String> = text("name")
    val fullName: Column<String> = text("full_name")
    val url: Column<String> = text("url")

    val user: Column<Long> = long("user_id") references GithubUser.id

    //TODO Use Ktor JSON
    val languages: Column<Map<String, Long>> = json("languages", Json.Default)


    override val primaryKey = PrimaryKey(id)
}