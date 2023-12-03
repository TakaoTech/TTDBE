package com.takaotech.plugins

import com.takaotech.di.getGeneralModule
import io.ktor.server.application.*
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(getGeneralModule(log), defaultModule)
    }
}