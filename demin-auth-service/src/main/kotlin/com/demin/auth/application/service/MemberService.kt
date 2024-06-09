package com.demin.auth.application.service

import com.demin.auth.application.port.`in`.FindMemberUseCase
import com.demin.auth.application.port.`in`.RegisterMemberUseCase
import com.demin.auth.application.port.`in`.UpdateMemberUseCase
import com.demin.auth.application.port.`in`.command.RegisterMemberCommand
import com.demin.auth.application.port.`in`.command.UpdateMemberCommand
import com.demin.auth.application.port.out.FindMemberPort
import com.demin.auth.application.port.out.SaveMemberPort
import com.demin.auth.application.port.out.UpdateMemberPort
import com.demin.auth.domain.Member
import com.demin.common.enums.MemberGrade
import com.demin.common.enums.MemberRole
import com.demin.common.enums.MemberStatus
import com.demin.common.exception.ResourceNotFoundException
import com.demin.common.util.SnowflakeIdGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val saveMemberPort: SaveMemberPort,
    private val updateMemberPort: UpdateMemberPort,
    private val findMemberPort: FindMemberPort,
    private val passwordEncoder: PasswordEncoder,
    private val snowflakeIdGenerator: SnowflakeIdGenerator
): RegisterMemberUseCase, UpdateMemberUseCase, FindMemberUseCase {

    @Transactional
    override fun registerMember(command: RegisterMemberCommand): Member {
        val memberId = snowflakeIdGenerator.nextId()
        val encryptedPassword = passwordEncoder.encode(command.password)

        val member = Member.generateMember(
            memberId = Member.MemberId(memberId),
            memberEmail = Member.MemberEmail(command.email),
            memberPassword = Member.MemberPassword(encryptedPassword),
            memberName = Member.MemberName(command.name),
            memberAddress = Member.MemberAddress(command.address),
            memberPhoneNumber = Member.MemberPhoneNumber(command.phoneNumber),
            memberNickname = Member.MemberNickname(command.nickname),
            grade = MemberGrade.BRONZE,
            status = MemberStatus.ACTIVE,
            role = MemberRole.CUSTOMER,
            lastLoginAt = null,
            failedLoginAttempts = 0,
            lockedUntil = null,
            refreshToken = null,
            permissions = emptyList()
        )

        return saveMemberPort.save(member)
    }

    @Transactional
    override fun updateMember(command: UpdateMemberCommand): Member {
        val existingMember = findMemberPort.findById(command.memberId)
            ?:throw ResourceNotFoundException("Member with id ${command.memberId} not found")

        val updatedPassword = command.password?.let { passwordEncoder.encode(it) }

        val updatedMember = existingMember.copy(
            email = command.email?.let { Member.MemberEmail(it) } ?: existingMember.email,
            password = updatedPassword?.let { Member.MemberPassword(it) } ?: existingMember.password,
            name = command.name?.let { Member.MemberName(it) } ?: existingMember.name,
            nickname = command.nickname?.let { Member.MemberNickname(it) } ?: existingMember.nickname,
            address = command.address?.let { Member.MemberAddress(it) } ?: existingMember.address,
            phoneNumber = command.phoneNumber?.let { Member.MemberPhoneNumber(it) } ?: existingMember.phoneNumber
        )

        return updateMemberPort.update(updatedMember)
    }

    override fun findMemberById(memberId: String): Member {
        return findMemberPort.findById(memberId)
            ?:throw ResourceNotFoundException("Member with id $memberId not found")
    }

    override fun findAllMembers(): List<Member> {
        return findMemberPort.findAll()
    }
}