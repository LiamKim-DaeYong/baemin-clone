package com.demin.auth.application.service.store

import com.demin.auth.application.port.incoming.storeowner.command.UpdateStoreOwnerInfoCommand
import com.demin.auth.application.port.outgoing.storeowner.FindStoreOwnerPort
import com.demin.auth.application.port.outgoing.storeowner.SaveStoreOwnerPort
import com.demin.auth.domain.StoreOwner
import com.demin.auth.domain.UserAccount
import com.demin.auth.testdata.TestStoreOwnerDataFactory.createTestRegisterStoreOwnerCommand
import com.demin.auth.testdata.TestStoreOwnerDataFactory.createTestStoreOwner
import com.demin.core.exception.ResourceNotFoundException
import com.demin.core.util.SnowflakeIdGenerator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class StoreOwnerCommandServiceTest :
    FunSpec({
        val saveStoreOwnerPort = mockk<SaveStoreOwnerPort>()
        val findStoreOwnerPort = mockk<FindStoreOwnerPort>()
        val snowflakeIdGenerator = mockk<SnowflakeIdGenerator>()
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

        val storeOwnerCommandService =
            StoreOwnerCommandService(
                saveStoreOwnerPort,
                findStoreOwnerPort,
                passwordEncoder,
                snowflakeIdGenerator,
            )

        test("registerStoreOwner should save and return StoreOwner") {
            val command = createTestRegisterStoreOwnerCommand()

            val storeOwnerId = "123456"
            every { snowflakeIdGenerator.nextId() } returns storeOwnerId

            val encryptedPassword = passwordEncoder.encode(command.password)

            val storeOwner =
                StoreOwner.create(
                    storeOwnerName = StoreOwner.StoreOwnerName(command.name),
                    storeOwnerAddress = StoreOwner.StoreOwnerAddress(command.address),
                    storeOwnerPhoneNumber = StoreOwner.StoreOwnerPhoneNumber(command.phoneNumber),
                    userAccount =
                        UserAccount.create(
                            id = UserAccount.UserAccountId(storeOwnerId),
                            email = UserAccount.UserEmail(command.email),
                            password = UserAccount.UserPassword(encryptedPassword),
                            role = UserAccount.UserRole(UserAccount.UserRole.Role.STORE_OWNER),
                        ),
                )

            every { saveStoreOwnerPort.save(any()) } returns storeOwner

            val savedStoreOwner = storeOwnerCommandService.registerStoreOwner(command)

            savedStoreOwner.id.value shouldBe storeOwnerId
            passwordEncoder.matches(command.password, savedStoreOwner.userAccount.password.value) shouldBe true
        }

        test("updateStoreOwner should update and return StoreOwner") {
            val storeOwnerId = "123456"
            val existingStoreOwner = createTestStoreOwner(storeOwnerId)

            every { findStoreOwnerPort.findById(storeOwnerId) } returns existingStoreOwner

            val command =
                UpdateStoreOwnerInfoCommand(
                    name = "Updated Name",
                    address = null,
                    phoneNumber = null,
                )

            val updatedStoreOwner =
                existingStoreOwner.updateStoreOwnerInfo(
                    newName = command.name?.let { StoreOwner.StoreOwnerName(it) },
                    newAddress = command.address?.let { StoreOwner.StoreOwnerAddress(it) },
                    newPhoneNumber = command.phoneNumber?.let { StoreOwner.StoreOwnerPhoneNumber(it) },
                )

            every { saveStoreOwnerPort.save(any()) } returns updatedStoreOwner

            val result = storeOwnerCommandService.updateStoreOwnerInfo(storeOwnerId, command)

            result.name.value shouldBe command.name
            result.address.value.let {
                it.address shouldBe existingStoreOwner.address.value.address
                it.detailAddress shouldBe existingStoreOwner.address.value.detailAddress
                it.zipCode shouldBe existingStoreOwner.address.value.zipCode
            }
            result.phoneNumber.value shouldBe existingStoreOwner.phoneNumber.value
        }

        test("updateStoreOwner should throw ResourceNotFoundException when StoreOwner not found") {
            val storeOwnerId = "123456"
            val command =
                UpdateStoreOwnerInfoCommand(
                    name = null,
                    address = null,
                    phoneNumber = null,
                )

            every { findStoreOwnerPort.findById(storeOwnerId) } returns null

            shouldThrow<ResourceNotFoundException> {
                storeOwnerCommandService.updateStoreOwnerInfo(storeOwnerId, command)
            }
        }
    })
