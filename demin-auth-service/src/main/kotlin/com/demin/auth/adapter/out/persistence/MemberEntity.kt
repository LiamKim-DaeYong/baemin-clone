package com.demin.auth.adapter.out.persistence

import com.demin.core.address.AddressEntity
import com.demin.core.AuditableEntity
import com.demin.core.enums.MemberGrade
import com.demin.core.enums.MemberRole
import com.demin.core.enums.MemberStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class MemberEntity(
    @Id
    @Column(name = "member_id", length = 36, nullable = false, updatable = false)
    val id: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val nickname: String,

    @Embedded
    val addressEntity: AddressEntity,

    @Column(nullable = false)
    val phoneNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: MemberStatus,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: MemberRole,

    @Column(nullable = true)
    val lastLoginAt: LocalDateTime? = null,

    @Column(nullable = false)
    val failedLoginAttempts: Int = 0,

    @Column(nullable = true)
    val lockedUntil: LocalDateTime? = null,

    @Column(nullable = true)
    val refreshToken: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val grade: MemberGrade
): AuditableEntity()
