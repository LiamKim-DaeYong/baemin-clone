package com.demin.auth.domain

import com.demin.core.address.AddressDto
import com.demin.core.enums.MemberGrade
import com.demin.core.enums.MemberRole
import com.demin.core.enums.MemberStatus
import java.time.LocalDateTime

data class Member private constructor(
    val memberId: MemberId,
    val email: MemberEmail,
    val password: MemberPassword,
    val name: MemberName,
    val address: AddressDto,
    val phoneNumber: MemberPhoneNumber,
    val nickname: MemberNickname,
    val grade: MemberGrade,
    val status: MemberStatus,
    val role: MemberRole,
    val lastLoginAt: LocalDateTime?,
    val failedLoginAttempts: Int,
    val lockedUntil: LocalDateTime?,
    val refreshToken: RefreshToken?,
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
            memberAddress: AddressDto,
            memberPhoneNumber: MemberPhoneNumber,
            memberNickname: MemberNickname,
            grade: MemberGrade,
            status: MemberStatus,
            role: MemberRole,
            lastLoginAt: LocalDateTime? = null,
            failedLoginAttempts: Int = 0,
            lockedUntil: LocalDateTime? = null,
            refreshToken: RefreshToken? = null,
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
                createdAt = createdAt,
                createdBy = createdBy,
                updatedAt = updatedAt,
                updatedBy = updatedBy
            )
        }
    }

    data class MemberId(val value: String)
    data class MemberEmail(val value: String)
    data class MemberPassword(val value: String)
    data class MemberName(val value: String)
    data class MemberPhoneNumber(val value: String)
    data class MemberNickname(val value: String)
    data class RefreshToken(val value: String)
}