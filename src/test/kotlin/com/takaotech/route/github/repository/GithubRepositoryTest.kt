package com.takaotech.route.github.repository

import com.takaotech.di.getGeneralModule
import com.takaotech.utils.LOGGER
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ksp.generated.defaultModule
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class GithubRepositoryTest : FunSpec(), KoinTest {
    override fun extensions() = listOf(KoinExtension(listOf(getGeneralModule(LOGGER), defaultModule)))

    val githubRepository by inject<GithubRepository>()

    init {
        test("Get Repository Start List") {
            val repoStars = githubRepository.getAllStarts()
            assertTrue { repoStars.isNotEmpty() }
        }

        test("Get Languages of Repository") {
            //It's Ktofit ID
            val languages = githubRepository.getLanguagesByRepository(203655484)
            LOGGER.info(Json.encodeToString(languages))
            assertTrue { languages.isNotEmpty() }
        }
    }

}