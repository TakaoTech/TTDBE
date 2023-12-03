package com.takaotech.route.github.data

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.json.json

object GithubDepositoryTable : IdTable<Long>() {
    /**
     * GitHub Repository Id
     */
    override val id: Column<EntityID<Long>> = long("id").entityId()

    val name: Column<String> = text("name")
    val fullName: Column<String> = text("full_name")
    val url: Column<String> = text("url")

    //    val user: Column<EntityID<Long>> = long("user_id").entityId() references GithubUserTable.id
    val user: Column<EntityID<Long>> = reference("user_id", GithubUserTable)

    //TODO Use Ktor JSON
    val languages: Column<Map<String, Long>> = json("languages", Json.Default)


    override val primaryKey = PrimaryKey(id)
}

class GithubDepositoryEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GithubDepositoryEntity>(GithubDepositoryTable)

    var name by GithubDepositoryTable.name
    var fullName by GithubDepositoryTable.fullName
    var url by GithubDepositoryTable.url
    var user by GithubDepositoryTable.user

    //    val userRef by GithubUserEntity referrersOn GithubUserTable.id
    var languages by GithubDepositoryTable.languages

}