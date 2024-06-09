package com.demin.auth.domain

import com.demin.auth.adapter.out.persistence.MemberEntity
import com.demin.common.enums.MemberGrade
import com.demin.common.enums.MemberRole
import com.demin.common.enums.MemberStatus
import java.time.LocalDateTime

data class Member private constructor(
    val memberId: MemberId,
    val email: MemberEmail,
    val password: MemberPassword,
    val name: MemberName,
    val address: MemberAddress,
    val phoneNumber: MemberPhoneNumber,
    val nickname: MemberNickname,
    val grade: MemberGrade,
    val status: MemberStatus,
    val role: MemberRole,
    val lastLoginAt: LocalDateTime?,
    val failedLoginAttempts: Int,
    val lockedUntil: LocalDateTime?,
    val refreshToken: RefreshToken?,
    val permissions: List<MemberPermission>,
    val createdAt: LocalDateTime?,
    val createdBy: String?,
    val updatedAt: LocalDateTime?,
    val updatedBy: String?,
) {
    companion object {
        fun generateMember(
            memberId: MemberId,
            memberEmail: MemberEmail,
            memberPassword: MemberPassword,
            memberName: MemberName,
            memberAddress: MemberAddress,
            memberPhoneNumber: MemberPhoneNumber,
            memberNickname: MemberNickname,
            grade: MemberGrade,
            status: MemberStatus,
            role: MemberRole,
            lastLoginAt: LocalDateTime? = null,
            failedLoginAttempts: Int = 0,
            lockedUntil: LocalDateTime? = null,
            refreshToken: RefreshToken? = null,
            permissions: List<MemberPermission> = listOf(),
            createdAt: LocalDateTime? = null,
            createdBy: String? = null,
            updatedAt: LocalDateTime? = null,
            updatedBy: String? = null
        ): Member {
            return Member(
                memberId = memberId,
                email = memberEmail,
                password = memberPassword,
                name = memberName,
                address = memberAddress,
                phoneNumber = memberPhoneNumber,
                nickname = memberNickname,
                grade = grade,
                status = status,
                role = role,
                lastLoginAt = lastLoginAt,
                failedLoginAttempts = failedLoginAttempts,
                lockedUntil = lockedUntil,
                refreshToken = refreshToken,
                permissions = permissions,
                createdAt = createdAt,
                createdBy = createdBy,
                updatedAt = updatedAt,
                updatedBy = updatedBy
            )
        }

        fun fromEntity(memberEntity: MemberEntity): Member {
            return generateMember(
                memberId = MemberId(memberEntity.id),
                memberEmail = MemberEmail(memberEntity.email),
                memberPassword = MemberPassword(memberEntity.password),
                memberName = MemberName(memberEntity.name),
                memberNickname = MemberNickname(memberEntity.nickname),
                memberAddress = MemberAddress(memberEntity.address),
                memberPhoneNumber = MemberPhoneNumber(memberEntity.phoneNumber),
                status = memberEntity.status,
                role = memberEntity.role,
                lastLoginAt = memberEntity.lastLoginAt,
                failedLoginAttempts = memberEntity.failedLoginAttempts,
                lockedUntil = memberEntity.lockedUntil,
                refreshToken = memberEntity.refreshToken?.let { RefreshToken(it) },
                permissions = memberEntity.permissions.map { MemberPermission(it) },
                grade = memberEntity.grade,
                createdAt = memberEntity.createdAt,
                createdBy = memberEntity.createdBy,
                updatedAt = memberEntity.updatedAt,
                updatedBy = memberEntity.updatedBy
            )
        }
    }

    data class MemberId(val value: String)
    data class MemberEmail(val value: String)
    data class MemberPassword(val value: String)
    data class MemberName(val value: String)
    data class MemberAddress(val value: String)
    data class MemberPhoneNumber(val value: String)
    data class MemberNickname(val value: String)
    data class MemberPermission(val value: String)
    data class RefreshToken(val value: String)

    fun toEntity(): MemberEntity {
        return MemberEntity(
            id = this.memberId.value,
            email = this.email.value,
            password = this.password.value,
            name = this.name.value,
            nickname = this.nickname.value,
            address = this.address.value,
            phoneNumber = this.phoneNumber.value,
            status = this.status,
            role = this.role,
            lastLoginAt = this.lastLoginAt,
            failedLoginAttempts = this.failedLoginAttempts,
            lockedUntil = this.lockedUntil,
            refreshToken = this.refreshToken?.value,
            permissions = this.permissions.map { it.value },
            grade = this.grade
        )
    }
}