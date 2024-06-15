package com.demin.auth.domain

import com.demin.auth.adapter.outgoing.persistence.useraccount.UserAccountJpaEntity
import java.time.LocalDateTime

class UserAccount private constructor(
    val id: UserAccountId,
    val email: UserEmail,
    val password: UserPassword,
    val role: UserRole,
    val status: UserStatus,
    val lastLoginAt: UserLastLoginAt?,
    val failedLoginAttempts: UserFailedLoginAttempts,
    val lockedUntil: UserLockedUntil?,
    val refreshToken: UserRefreshToken?,
) {
    companion object {
        fun create(
            id: UserAccountId,
            email: UserEmail,
            password: UserPassword,
            role: UserRole,
        ): UserAccount =
            UserAccount(
                id = id,
                email = email,
                password = password,
                role = role,
                status = UserStatus(UserStatus.Statue.ACTIVE),
                lastLoginAt = null,
                failedLoginAttempts = UserFailedLoginAttempts(0),
                lockedUntil = null,
                refreshToken = null,
            )

        fun fromJpaEntity(jpaEntity: UserAccountJpaEntity): UserAccount =
            UserAccount(
                id = UserAccountId(jpaEntity.id),
                email = UserEmail(jpaEntity.email),
                password = UserPassword(jpaEntity.password),
                role = UserRole(jpaEntity.role),
                status = UserStatus(jpaEntity.status),
                lastLoginAt = jpaEntity.lastLoginAt?.let { UserLastLoginAt(it) },
                failedLoginAttempts = UserFailedLoginAttempts(jpaEntity.failedLoginAttempts),
                lockedUntil = jpaEntity.lockedUntil?.let { UserLockedUntil(it) },
                refreshToken = jpaEntity.refreshToken?.let { UserRefreshToken(it) },
            )
    }

    data class UserAccountId(
        val value: String,
    )

    data class UserEmail(
        val value: String,
    )

    data class UserPassword(
        val value: String,
    )

    data class UserLastLoginAt(
        val value: LocalDateTime,
    )

    data class UserFailedLoginAttempts(
        val value: Int,
    )

    data class UserLockedUntil(
        val value: LocalDateTime,
    )

    data class UserRefreshToken(
        val value: String,
    )

    data class UserRole(
        val value: Role,
    ) {
        enum class Role {
            CUSTOMER,
            RIDER,
            ADMIN,
            STORE_OWNER,
        }
    }

    data class UserStatus(
        val value: Statue,
    ) {
        enum class Statue {
            ACTIVE,
            INACTIVE,
            SUSPENDED,
            LOCKED,
        }
    }
}
