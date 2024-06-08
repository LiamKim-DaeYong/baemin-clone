package com.demin.common.hexagonal.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RestController

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RestController
annotation class ExternalSystemAdapter(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)
