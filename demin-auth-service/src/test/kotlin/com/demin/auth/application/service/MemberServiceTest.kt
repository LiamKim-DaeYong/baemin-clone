package com.demin.auth.application.service

import com.demin.auth.application.port.`in`.command.RegisterMemberCommand
import com.demin.auth.application.port.`in`.command.UpdateMemberCommand
import com.demin.auth.application.port.out.FindMemberPort
import com.demin.auth.application.port.out.SaveMemberPort
import com.demin.auth.application.port.out.UpdateMemberPort
import com.demin.auth.domain.Member
import com.demin.common.address.AddressDto
import com.demin.common.enums.MemberGrade
import com.demin.common.enums.MemberRole
import com.demin.common.enums.MemberStatus
import com.demin.common.exception.ResourceNotFoundException
import com.demin.common.util.SnowflakeIdGenerator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class MemberServiceTest : FunSpec({
    val saveMemberPort = mockk<SaveMemberPort>()
    val updateMemberPort = mockk<UpdateMemberPort>()
    val findMemberPort = mockk<FindMemberPort>()
    val snowflakeIdGenerator = mockk<SnowflakeIdGenerator>()
    val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    val memberService = MemberService(
        saveMemberPort,
        updateMemberPort,
        findMemberPort,
        passwordEncoder,
        snowflakeIdGenerator
    )

    test("registerMember should save and return member") {
        val command = RegisterMemberCommand(
            email = "test@example.com",
            password = "password123",
            name = "John Doe",
            address = AddressDto(
                zipCode = "123",
                address = "Main St",
                detailAddress = "123-45"
            ),
            phoneNumber = "1234567890",
            nickname = "johndoe"
        )

        val memberId = "123456"
        every { snowflakeIdGenerator.nextId() } returns memberId

        val encryptedPassword = passwordEncoder.encode(command.password)

        val member = Member.generateMember(
            memberId = Member.MemberId(memberId),
            memberEmail = Member.MemberEmail(command.email),
            memberPassword = Member.MemberPassword(encryptedPassword),
            memberName = Member.MemberName(command.name),
            memberAddress = command.address,
            memberPhoneNumber = Member.MemberPhoneNumber(command.phoneNumber),
            memberNickname = Member.MemberNickname(command.nickname),
            grade = MemberGrade.BRONZE,
            status = MemberStatus.ACTIVE,
            role = MemberRole.CUSTOMER
        )

        every { saveMemberPort.save(any()) } returns member

        val savedMember = memberService.registerMember(command)

        savedMember.memberId.value shouldBe memberId
        passwordEncoder.matches(command.password, savedMember.password.value) shouldBe true
    }

    test("updateMember should update and return member") {
        val memberId = "123456"
        val existingMember = Member.generateMember(
            memberId = Member.MemberId(memberId),
            memberEmail = Member.MemberEmail("test@example.com"),
            memberPassword = Member.MemberPassword(passwordEncoder.encode("password123")),
            memberName = Member.MemberName("John Doe"),
            memberAddress = AddressDto(
                zipCode = "123",
                address = "Main St",
                detailAddress = "123-45"
            ),
            memberPhoneNumber = Member.MemberPhoneNumber("1234567890"),
            memberNickname = Member.MemberNickname("johndoe"),
            grade = MemberGrade.BRONZE,
            status = MemberStatus.ACTIVE,
            role = MemberRole.CUSTOMER
        )

        every { findMemberPort.findById(memberId) } returns existingMember

        val command = UpdateMemberCommand(
            memberId = memberId,
            email = "updated@example.com",
            password = "newpassword123",
            name = "Updated Name",
            nickname = null,
            address =  null,
            phoneNumber = null
        )

        val updatedPassword = passwordEncoder.encode(command.password)

        val updatedMember = existingMember.copy(
            email = Member.MemberEmail(command.email!!),
            password = Member.MemberPassword(updatedPassword),
            name = Member.MemberName(command.name!!)
        )

        every { updateMemberPort.update(any()) } returns updatedMember

        val result = memberService.updateMember(command)

        result.email.value shouldBe command.email
        passwordEncoder.matches(command.password, result.password.value) shouldBe true
        result.name.value shouldBe command.name
        result.nickname.value shouldBe existingMember.nickname.value
    }

    test("updateMember should throw ResourceNotFoundException when member not found") {
        val memberId = "123456"
        val command = UpdateMemberCommand(
            memberId = memberId,
            email = "updated@example.com",
            password = null,
            name = null,
            nickname = null,
            address = null,
            phoneNumber = null
        )

        every { findMemberPort.findById(memberId) } returns null

        shouldThrow<ResourceNotFoundException> {
            memberService.updateMember(command)
        }
    }

    test("findMemberById should return member when found") {
        val memberId = "123456"
        val member = Member.generateMember(
            memberId = Member.MemberId(memberId),
            memberEmail = Member.MemberEmail("test@example.com"),
            memberPassword = Member.MemberPassword("password123"),
            memberName = Member.MemberName("John Doe"),
            memberAddress = AddressDto(
                zipCode = "123",
                address = "Main St",
                detailAddress = "123-45"
            ),
            memberPhoneNumber = Member.MemberPhoneNumber("1234567890"),
            memberNickname = Member.MemberNickname("johndoe"),
            grade = MemberGrade.BRONZE,
            status = MemberStatus.ACTIVE,
            role = MemberRole.CUSTOMER
        )

        every { findMemberPort.findById(memberId) } returns member

        val result = memberService.findMemberById(memberId)

        result.memberId.value shouldBe memberId
    }

    test("findMemberById should throw ResourceNotFoundException when member not found") {
        val memberId = "123456"
        every { findMemberPort.findById(memberId) } returns null

        shouldThrow<ResourceNotFoundException> {
            memberService.findMemberById(memberId)
        }
    }
})