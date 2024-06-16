package com.demin.auth.mapper

import com.demin.auth.adapter.outgoing.persistence.useraccount.UserAccountJpaEntity
import com.demin.auth.domain.UserAccount

fun UserAccount.toEntity() =
    UserAccountJpaEntity(
        id = id.value,
        email = email.value,
        password = password.value,
        role = role.value,
        status = status.value,
        lastLoginAt = lastLoginAt?.value,
        failedLoginAttempts = failedLoginAttempts.value,
        lockedUntil = lockedUntil?.value,
        refreshToken = refreshToken?.value,
    )

fun UserAccountJpaEntity.toDomain() = UserAccount.fromJpaEntity(this)
