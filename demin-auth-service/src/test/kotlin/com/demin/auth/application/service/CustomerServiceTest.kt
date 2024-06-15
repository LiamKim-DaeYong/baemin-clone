package com.demin.auth.application.service

import com.demin.auth.application.port.out.customer.FindCustomerPort
import com.demin.auth.application.port.out.customer.SaveCustomerPort
import com.demin.auth.application.port.out.customer.UpdateCustomerPort
import com.demin.core.util.SnowflakeIdGenerator
import io.kotest.core.spec.style.FunSpec
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class CustomerServiceTest : FunSpec({
    val saveCustomerPort = mockk<SaveCustomerPort>()
    val updateCustomerPort = mockk<UpdateCustomerPort>()
    val findCustomerPort = mockk<FindCustomerPort>()
    val snowflakeIdGenerator = mockk<SnowflakeIdGenerator>()
    val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

//    val customerCommandService = CustomerCommandService(
//        saveCustomerPort,
//        updateCustomerPort,
//        findCustomerPort,
//        passwordEncoder,
//        snowflakeIdGenerator
//    )
//
//    test("registerMember should save and return member") {
//        val command = RegisterCustomerCommand(
//            email = "test@example.com",
//            password = "password123",
//            name = "John Doe",
//            address = AddressDto(
//                zipCode = "123",
//                address = "Main St",
//                detailAddress = "123-45"
//            ),
//            phoneNumber = "1234567890",
//            nickname = "johndoe"
//        )
//
//        val memberId = "123456"
//        every { snowflakeIdGenerator.nextId() } returns memberId
//
//        val encryptedPassword = passwordEncoder.encode(command.password)
//
//        val member = Member.generateMember(
//            memberId = Member.Id(memberId),
//            memberEmail = Member.Email(command.email),
//            memberPassword = Member.Password(encryptedPassword),
//            memberName = Member.Name(command.name),
//            memberAddress = command.address,
//            memberPhoneNumber = Member.PhoneNumber(command.phoneNumber),
//            memberNickname = Member.MemberNickname(command.nickname),
//            grade = MemberGrade.BRONZE,
//            status = Status.ACTIVE,
//            role = MemberRole.CUSTOMER
//        )
//
//        every { saveCustomerPort.save(any()) } returns member
//
//        val savedMember = customerCommandService.registerCustomer(command)
//
//        savedMember.id.value shouldBe memberId
//        passwordEncoder.matches(command.password, savedMember.password.value) shouldBe true
//    }
//
//    test("updateMember should update and return member") {
//        val memberId = "123456"
//        val existingMember = Member.generateMember(
//            memberId = Member.Id(memberId),
//            memberEmail = Member.Email("test@example.com"),
//            memberPassword = Member.Password(passwordEncoder.encode("password123")),
//            memberName = Member.Name("John Doe"),
//            memberAddress = AddressDto(
//                zipCode = "123",
//                address = "Main St",
//                detailAddress = "123-45"
//            ),
//            memberPhoneNumber = Member.PhoneNumber("1234567890"),
//            memberNickname = Member.MemberNickname("johndoe"),
//            grade = MemberGrade.BRONZE,
//            status = Status.ACTIVE,
//            role = MemberRole.CUSTOMER
//        )
//
//        every { findCustomerPort.findById(memberId) } returns existingMember
//
//        val command = UpdateCustomerCommand(
//            customerId = memberId,
//            email = "updated@example.com",
//            password = "newpassword123",
//            name = "Updated Name",
//            nickname = null,
//            address =  null,
//            phoneNumber = null
//        )
//
//        val updatedPassword = passwordEncoder.encode(command.password)
//
//        val updatedMember = existingMember.copy(
//            memberEmail = Member.Email(command.email!!),
//            memberPassword = Member.Password(updatedPassword),
//            memberName = Member.Name(command.name!!)
//        )
//
//        every { updateCustomerPort.update(any()) } returns updatedMember
//
//        val result = customerCommandService.updateCustomer(command)
//
//        result.email.value shouldBe command.email
//        passwordEncoder.matches(command.password, result.password.value) shouldBe true
//        result.name.value shouldBe command.name
//        result.memberNickname.value shouldBe existingMember.memberNickname.value
//    }
//
//    test("updateMember should throw ResourceNotFoundException when member not found") {
//        val memberId = "123456"
//        val command = UpdateCustomerCommand(
//            customerId = memberId,
//            email = "updated@example.com",
//            password = null,
//            name = null,
//            nickname = null,
//            address = null,
//            phoneNumber = null
//        )
//
//        every { findCustomerPort.findById(memberId) } returns null
//
//        shouldThrow<ResourceNotFoundException> {
//            customerCommandService.updateCustomer(command)
//        }
//    }
//
//    test("findMemberById should return member when found") {
//        val memberId = "123456"
//        val member = Member.generateMember(
//            memberId = Member.Id(memberId),
//            memberEmail = Member.Email("test@example.com"),
//            memberPassword = Member.Password("password123"),
//            memberName = Member.Name("John Doe"),
//            memberAddress = AddressDto(
//                zipCode = "123",
//                address = "Main St",
//                detailAddress = "123-45"
//            ),
//            memberPhoneNumber = Member.PhoneNumber("1234567890"),
//            memberNickname = Member.MemberNickname("johndoe"),
//            grade = MemberGrade.BRONZE,
//            status = Status.ACTIVE,
//            role = MemberRole.CUSTOMER
//        )
//
//        every { findCustomerPort.findById(memberId) } returns member
//
//        val result = customerCommandService.findCustomerById(memberId)
//
//        result.id.value shouldBe memberId
//    }
//
//    test("findMemberById should throw ResourceNotFoundException when member not found") {
//        val memberId = "123456"
//        every { findCustomerPort.findById(memberId) } returns null
//
//        shouldThrow<ResourceNotFoundException> {
//            customerCommandService.findCustomerById(memberId)
//        }
//    }
})
