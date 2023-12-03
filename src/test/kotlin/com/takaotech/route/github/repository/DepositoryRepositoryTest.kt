package com.takaotech.route.github.repository

import com.takaotech.di.getGeneralModule
import com.takaotech.route.github.data.GithubDepositoryTable
import com.takaotech.route.github.data.GithubUserTable
import com.takaotech.route.github.model.GHRepository
import com.takaotech.route.github.model.GHUser
import com.takaotech.utils.LOGGER
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ksp.generated.defaultModule
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class DepositoryRepositoryTest : FunSpec(), KoinTest {
    override fun extensions() = listOf(KoinExtension(listOf(getGeneralModule(LOGGER), defaultModule)))

    val depositoryRepository by inject<DepositoryRepository>()
    val database by inject<Database>()

    init {
        test("Save data in DB") {
            transaction(database) {
                SchemaUtils.create(GithubDepositoryTable, GithubUserTable)
            }

            val repository = listOf(
                GHRepository(
                    id = 1254,
                    name = "Tracy Henson",
                    fullName = "Clay Olsen",
                    url = "https://www.bing.com/search?q=splendide",
                    user = GHUser(
                        id = 1744, name = "Katharine Hampton", url = "https://duckduckgo.com/?q=blandit"
                    ),
                    languages = mapOf(
                        "Kotlin" to 20
                    )
                )
            )

            depositoryRepository.saveAllRepositoryToDB(repository)
            val recoveredRepo = depositoryRepository.getAllDepository()
            assertTrue { recoveredRepo.isNotEmpty() }
        }
    }

}