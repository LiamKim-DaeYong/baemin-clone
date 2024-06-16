package com.demin.auth.application.service.customer

import com.demin.auth.application.port.outgoing.customer.FindCustomerPort
import com.demin.auth.testdata.TestCustomerDataFactory.createTestCustomer
import com.demin.core.exception.ResourceNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CustomerQueryServiceTest :
    FunSpec({
        val findCustomerPort = mockk<FindCustomerPort>()

        val customerQueryService =
            CustomerQueryService(findCustomerPort)

        test("findCustomerById should return customer when found") {
            val customerId = "123456"
            val customer = createTestCustomer(customerId)

            every { findCustomerPort.findById(customerId) } returns customer

            val result = customerQueryService.findCustomerById(customerId)

            result.id.value shouldBe customerId
        }

        test("findCustomerById should throw ResourceNotFoundException when customer not found") {
            val customerId = "123456"
            every { findCustomerPort.findById(customerId) } returns null

            shouldThrow<ResourceNotFoundException> {
                customerQueryService.findCustomerById(customerId)
            }
        }

        test("findAllCustomers should return all customers") {
            val customer1 = createTestCustomer("123456")
            val customer2 = createTestCustomer("654321")

            every { findCustomerPort.findAll() } returns listOf(customer1, customer2)

            val result = customerQueryService.findAllCustomers()

            result.size shouldBe 2
            result[0].id.value shouldBe "123456"
            result[1].id.value shouldBe "654321"
        }

        test("findAllCustomers should return empty list when no customers found") {
            every { findCustomerPort.findAll() } returns emptyList()

            val result = customerQueryService.findAllCustomers()

            result shouldBe emptyList()
        }
    })
