package com.takaotech.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.kohsuke.github.GHRepository

fun GHRepository.serializeToJson(): String {
    return ObjectMapper()
        .writeValueAsString(this)
}