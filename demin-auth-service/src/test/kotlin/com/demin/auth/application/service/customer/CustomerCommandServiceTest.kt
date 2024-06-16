package com.demin.auth.application.service.customer

import com.demin.auth.application.port.incoming.customer.command.UpdateCustomerInfoCommand
import com.demin.auth.application.port.outgoing.customer.FindCustomerPort
import com.demin.auth.application.port.outgoing.customer.SaveCustomerPort
import com.demin.auth.domain.Customer
import com.demin.auth.domain.UserAccount
import com.demin.auth.testdata.TestCustomerDataFactory.createTestCustomer
import com.demin.auth.testdata.TestCustomerDataFactory.createTestRegisterCustomerCommand
import com.demin.core.exception.ResourceNotFoundException
import com.demin.core.util.SnowflakeIdGenerator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class CustomerCommandServiceTest :
    FunSpec({
        val saveCustomerPort = mockk<SaveCustomerPort>()
        val findCustomerPort = mockk<FindCustomerPort>()
        val snowflakeIdGenerator = mockk<SnowflakeIdGenerator>()
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

        val customerCommandService =
            CustomerCommandService(
                saveCustomerPort,
                findCustomerPort,
                passwordEncoder,
                snowflakeIdGenerator,
            )

        test("registerCustomer should save and return customer") {
            val command = createTestRegisterCustomerCommand()

            val customerId = "123456"
            every { snowflakeIdGenerator.nextId() } returns customerId

            val encryptedPassword = passwordEncoder.encode(command.password)

            val customer =
                Customer.create(
                    customerName = Customer.CustomerName(command.name),
                    customerNickname = Customer.CustomerNickname(command.nickname),
                    customerAddress = Customer.CustomerAddress(command.address),
                    customerPhoneNumber = Customer.CustomerPhoneNumber(command.phoneNumber),
                    userAccount =
                        UserAccount.create(
                            id = UserAccount.UserAccountId(customerId),
                            email = UserAccount.UserEmail(command.email),
                            password = UserAccount.UserPassword(encryptedPassword),
                            role = UserAccount.UserRole(UserAccount.UserRole.Role.CUSTOMER),
                        ),
                )

            every { saveCustomerPort.save(any()) } returns customer

            val savedCustomer = customerCommandService.registerCustomer(command)

            savedCustomer.id.value shouldBe customerId
            passwordEncoder.matches(command.password, savedCustomer.userAccount.password.value) shouldBe true
        }

        test("updateCustomer should update and return customer") {
            val customerId = "123456"
            val existingCustomer = createTestCustomer(customerId)

            every { findCustomerPort.findById(customerId) } returns existingCustomer

            val command =
                UpdateCustomerInfoCommand(
                    name = "Updated Name",
                    nickname = null,
                    address = null,
                    phoneNumber = null,
                )

            val updatedCustomer =
                existingCustomer.updateCustomerInfo(
                    newName = command.name?.let { Customer.CustomerName(it) },
                    newNickname = command.nickname?.let { Customer.CustomerNickname(it) },
                    newAddress = command.address?.let { Customer.CustomerAddress(it) },
                    newPhoneNumber = command.phoneNumber?.let { Customer.CustomerPhoneNumber(it) },
                )

            every { saveCustomerPort.save(any()) } returns updatedCustomer

            val result = customerCommandService.updateCustomerInfo(customerId, command)

            result.name.value shouldBe command.name
            result.nickname.value shouldBe existingCustomer.nickname.value
            result.address.value.let {
                it.address shouldBe existingCustomer.address.value.address
                it.detailAddress shouldBe existingCustomer.address.value.detailAddress
                it.zipCode shouldBe existingCustomer.address.value.zipCode
            }
            result.phoneNumber.value shouldBe existingCustomer.phoneNumber.value
        }

        test("updateCustomer should throw ResourceNotFoundException when customer not found") {
            val customerId = "123456"
            val command =
                UpdateCustomerInfoCommand(
                    name = null,
                    nickname = null,
                    address = null,
                    phoneNumber = null,
                )

            every { findCustomerPort.findById(customerId) } returns null

            shouldThrow<ResourceNotFoundException> {
                customerCommandService.updateCustomerInfo(customerId, command)
            }
        }
    })
