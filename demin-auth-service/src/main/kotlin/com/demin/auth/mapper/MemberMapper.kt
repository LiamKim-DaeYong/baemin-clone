package com.demin.auth.mapper

import com.demin.auth.adapter.out.persistence.MemberEntity
import com.demin.auth.domain.Member
import com.demin.auth.domain.Member.*
import com.demin.auth.domain.Member.Companion.generateMember

fun MemberEntity.toMember(): Member {
    return generateMember(
        memberId = MemberId(this.id),
        memberEmail = MemberEmail(this.email),
        memberPassword = MemberPassword(this.password),
        memberName = MemberName(this.name),
        memberNickname = MemberNickname(this.nickname),
        memberAddress = this.addressEntity.toDto(),
        memberPhoneNumber = MemberPhoneNumber(this.phoneNumber),
        status = this.status,
        role = this.role,
        lastLoginAt = this.lastLoginAt,
        failedLoginAttempts = this.failedLoginAttempts,
        lockedUntil = this.lockedUntil,
        refreshToken = this.refreshToken?.let { RefreshToken(it) },
        grade = this.grade,
        createdAt = this.createdAt,
        createdBy = this.createdBy,
        updatedAt = this.updatedAt,
        updatedBy = this.updatedBy
    )
}

fun Member.toEntity(): MemberEntity {
    return MemberEntity(
        id = this.memberId.value,
        email = this.email.value,
        password = this.password.value,
        name = this.name.value,
        nickname = this.nickname.value,
        addressEntity = this.address.toEntity(),
        phoneNumber = this.phoneNumber.value,
        status = this.status,
        role = this.role,
        lastLoginAt = this.lastLoginAt,
        failedLoginAttempts = this.failedLoginAttempts,
        lockedUntil = this.lockedUntil,
        refreshToken = this.refreshToken?.value,
        grade = this.grade
    )
}
