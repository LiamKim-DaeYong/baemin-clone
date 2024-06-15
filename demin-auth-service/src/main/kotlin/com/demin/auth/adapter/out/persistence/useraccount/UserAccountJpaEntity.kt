package com.demin.auth.adapter.out.persistence.useraccount

import com.demin.auth.domain.UserAccount.UserRole.Role
import com.demin.auth.domain.UserAccount.UserStatus.Statue
import com.demin.core.AuditableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(name = "user_account", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class UserAccountJpaEntity(
    @Id
    @Column(name = "user_account_id", length = 36, nullable = false, updatable = false)
    val id: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val password: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: Statue,
    @Column(nullable = true)
    val lastLoginAt: LocalDateTime? = null,
    @Column(nullable = false)
    val failedLoginAttempts: Int = 0,
    @Column(nullable = true)
    val lockedUntil: LocalDateTime? = null,
    @Column(nullable = true)
    val refreshToken: String? = null,
) : AuditableEntity()
