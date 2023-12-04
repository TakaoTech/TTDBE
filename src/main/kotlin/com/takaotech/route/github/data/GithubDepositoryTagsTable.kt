package com.takaotech.route.github.data

import org.jetbrains.exposed.sql.Table

object GithubDepositoryTagsTable : Table() {
    val depository = reference("depository", GithubDepositoryTable)
    val tag = reference("tag", TagsTable)

    override val primaryKey = PrimaryKey(depository, tag)
}