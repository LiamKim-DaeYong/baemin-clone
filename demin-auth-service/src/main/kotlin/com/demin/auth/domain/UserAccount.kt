package com.demin.auth.domain

import java.time.LocalDateTime

class UserAccount private constructor(
    val id: UserAccountId,
    val email: UserEmail,
    val password: UserPassword,
    val role: UserRole,
    val status: UserStatus = UserStatus(UserStatus.Statue.ACTIVE),
    val lastLoginAt: UserLastLoginAt? = null,
    val failedLoginAttempts: UserFailedLoginAttempts = UserFailedLoginAttempts(0),
    val lockedUntil: UserLockedUntil? = null,
    val refreshToken: UserRefreshToken? = null,
) {
    companion object {
        fun create(
            id: UserAccountId,
            email: UserEmail,
            password: UserPassword,
            role: UserRole,
        ): UserAccount {
            return UserAccount(id, email, password, role)
        }
    }

    data class UserAccountId(val value: String)

    data class UserEmail(val value: String)

    data class UserPassword(val value: String)

    data class UserLastLoginAt(val value: LocalDateTime)

    data class UserFailedLoginAttempts(val value: Int)

    data class UserLockedUntil(val value: LocalDateTime)

    data class UserRefreshToken(val value: String)

    data class UserRole(val value: Role) {
        enum class Role {
            CUSTOMER,
            RIDER,
            ADMIN,
            STORE_OWNER,
        }
    }

    data class UserStatus(val value: Statue) {
        enum class Statue {
            ACTIVE,
            INACTIVE,
            SUSPENDED,
            LOCKED,
        }
    }
}
