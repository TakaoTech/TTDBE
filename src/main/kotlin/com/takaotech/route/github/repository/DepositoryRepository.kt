package com.takaotech.route.github.repository

import com.takaotech.route.github.data.GithubDepositoryEntity
import com.takaotech.route.github.data.GithubUserEntity
import com.takaotech.route.github.model.GHRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Factory

@Factory
class DepositoryRepository(private val database: Database) {

    fun saveAllRepositoryToDB(repositoryList: List<GHRepository>) {
        transaction(database) {
            val users = repositoryList.map {
                it.user.let { user ->
                    GithubUserEntity.new(user.id) {
                        name = user.name
                        url = user.url
                    }
                }
            }

            commit()

            repositoryList.forEach {
                GithubDepositoryEntity.new(it.id) {
                    name = it.name
                    fullName = it.fullName
                    url = it.url
                    user = users.first { user ->
                        user.id.value == it.user.id
                    }.id
                    languages = it.languages
                }
            }
        }
    }

    fun getAllDepository(): List<GithubDepositoryEntity> {
        return transaction {
            GithubDepositoryEntity.all()
                .toList()
        }
    }
}