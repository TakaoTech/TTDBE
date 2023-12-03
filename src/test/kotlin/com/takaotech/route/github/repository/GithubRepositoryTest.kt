package com.takaotech.route.github.repository

import com.takaotech.di.generalModule
import com.takaotech.utils.serializeToJson
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import org.koin.ksp.generated.defaultModule
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class GithubRepositoryTest : FunSpec(), KoinTest {
    override fun extensions() = listOf(KoinExtension(listOf(generalModule, defaultModule)))

    val githubRepository by inject<GithubRepository>()

    init {
        test("Get Repository List") {
            val repoStars = githubRepository.getAllStarts()

            println(
                "Serial object"
            )

            println(
                repoStars.first()
                    .serializeToJson()
            )

            assertTrue { repoStars.isNotEmpty() }
        }
    }

}