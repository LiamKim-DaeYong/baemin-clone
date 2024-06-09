package com.demin.core.config

import com.demin.core.util.SnowflakeIdGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun snowflakeIdGenerator(): SnowflakeIdGenerator {
        val workerId = System.getenv("WORKER_ID")?.toLong() ?: 1L
        val datacenterId = System.getenv("DATACENTER_ID")?.toLong() ?: 1L
        return SnowflakeIdGenerator(workerId, datacenterId)
    }
}