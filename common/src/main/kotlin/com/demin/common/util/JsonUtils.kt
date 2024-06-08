package com.demin.common.util

import com.fasterxml.jackson.databind.ObjectMapper

object JsonUtils {
    private val objectMapper = ObjectMapper()

    fun <T> toJson(value: T): String {
        return objectMapper.writeValueAsString(value)
    }

    fun <T> fromJson(value: String, clazz: Class<T>): T {
        return objectMapper.readValue(value, clazz)
    }
}