package com.jean.reactivecoroutinestudy.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SerializationConfiguration {

    @Bean
    fun jsonObjectMapper(): Json {
        val json = Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
            encodeDefaults = true
        }
        return json
    }
}
