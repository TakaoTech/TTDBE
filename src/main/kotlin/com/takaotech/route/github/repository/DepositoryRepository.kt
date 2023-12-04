package com.takaotech.route.github.repository

import com.takaotech.route.github.data.GithubDepositoryEntity
import com.takaotech.route.github.data.GithubDepositoryTable
import com.takaotech.route.github.data.GithubUserEntity
import com.takaotech.route.github.data.TagsEntity
import com.takaotech.route.github.model.GHRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Factory

@Factory
class DepositoryRepository(private val database: Database) {

    fun saveAllRepositoryToDB(repositoryList: List<GHRepository>) {
        transaction(database) {
            val repositoryListFiltered = repositoryList.map {
                it.id
            }.let {
                GithubDepositoryTable.slice(GithubDepositoryTable.id)
                    .selectAll()
                    .map {
                        it[GithubDepositoryTable.id].value
                    }
            }.let { exclusionList ->
                repositoryList.filterNot {
                    exclusionList.contains(it.id)
                }
            }

            val users = repositoryListFiltered.map {
                it.user.let { user ->
                    GithubUserEntity.findById(user.id) ?: GithubUserEntity.new(user.id) {
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

    fun setTagsAtRepository(repositoryId: Long, tags: List<TagsEntity>) {
        transaction {
            GithubDepositoryEntity.findById(repositoryId)?.let {
                it.tags = SizedCollection(tags)
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