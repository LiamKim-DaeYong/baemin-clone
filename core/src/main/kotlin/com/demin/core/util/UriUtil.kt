package com.demin.core.util

import org.springframework.web.servlet.support.ServletUriComponentsBuilder

object UriUtil {
    fun createLocationUri(
        path: String,
        id: String,
    ) = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path(path)
        .buildAndExpand(id)
        .toUri()
}
