package com.demin.auth.application.service.store

import com.demin.auth.application.port.outgoing.storeowner.FindStoreOwnerPort
import com.demin.auth.testdata.TestStoreOwnerDataFactory.createTestStoreOwner
import com.demin.core.exception.ResourceNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class StoreOwnerQueryServiceTest :
    FunSpec({
        val findStoreOwnerPort = mockk<FindStoreOwnerPort>()

        val storeOwnerQueryService =
            StoreOwnerQueryService(findStoreOwnerPort)

        test("findStoreOwnerById should return StoreOwner when found") {
            val storeOwnerId = "123456"
            val storeOwner = createTestStoreOwner(storeOwnerId)

            every { findStoreOwnerPort.findById(storeOwnerId) } returns storeOwner

            val result = storeOwnerQueryService.findStoreOwnerById(storeOwnerId)

            result.id.value shouldBe storeOwnerId
        }

        test("findStoreOwnerById should throw ResourceNotFoundException when StoreOwner not found") {
            val storeOwnerId = "123456"
            every { findStoreOwnerPort.findById(storeOwnerId) } returns null

            shouldThrow<ResourceNotFoundException> {
                storeOwnerQueryService.findStoreOwnerById(storeOwnerId)
            }
        }

        test("findAllStoreOwners should return all StoreOwners") {
            val storeOwner1 = createTestStoreOwner("123456")
            val storeOwner2 = createTestStoreOwner("654321")

            every { findStoreOwnerPort.findAll() } returns listOf(storeOwner1, storeOwner2)

            val result = storeOwnerQueryService.findAllStoreOwners()

            result.size shouldBe 2
            result[0].id.value shouldBe "123456"
            result[1].id.value shouldBe "654321"
        }

        test("findAllStoreOwners should return empty list when no StoreOwners found") {
            every { findStoreOwnerPort.findAll() } returns emptyList()

            val result = storeOwnerQueryService.findAllStoreOwners()

            result shouldBe emptyList()
        }
    })
