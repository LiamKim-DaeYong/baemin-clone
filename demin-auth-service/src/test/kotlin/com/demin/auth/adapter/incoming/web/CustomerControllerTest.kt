package com.demin.auth.adapter.incoming.web

import com.demin.auth.application.port.incoming.customer.command.UpdateCustomerInfoCommand
import com.demin.auth.application.service.customer.CustomerCommandService
import com.demin.auth.application.service.customer.CustomerQueryService
import com.demin.auth.config.SecurityConfig
import com.demin.auth.domain.Customer
import com.demin.auth.testdata.TestCustomerDataFactory.createTestCustomer
import com.demin.auth.testdata.TestCustomerDataFactory.createTestRegisterCustomerCommand
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import org.hamcrest.Matchers.containsInAnyOrder
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(
    controllers = [CustomerController::class],
    excludeFilters = [
        ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [SecurityConfig::class]),
    ],
)
class CustomerControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean private val customerQueryService: CustomerQueryService,
    @MockkBean private val customerCommandService: CustomerCommandService,
) : FunSpec({

        context("GET /api/v1/customers") {
            test("should return all customers") {
                val customers =
                    listOf(
                        createTestCustomer("customerId1"),
                        createTestCustomer("customerId2"),
                        createTestCustomer("customerId3"),
                    )

                every { customerQueryService.findAllCustomers() } returns customers

                mockMvc
                    .perform(get("/api/v1/customers"))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data").isArray)
                    .andExpect(jsonPath("$.data[0].id.value").value(customers[0].id.value))
            }

            test("should return a customer by id") {
                val customer = createTestCustomer("customerId1")
                every { customerQueryService.findCustomerById("1") } returns customer

                mockMvc
                    .perform(get("/api/v1/customers/1"))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data.id.value").value(customer.id.value))
            }
        }

        context("POST /api/v1/customers") {
            test("should register a new customer") {
                val command = createTestRegisterCustomerCommand()

                val customer = createTestCustomer("123456")
                every { customerCommandService.registerCustomer(any()) } returns customer

                mockMvc
                    .perform(
                        post("/api/v1/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)),
                    ).andExpect(status().isCreated)
                    .andExpect(header().exists("Location"))
                    .andExpect(jsonPath("$.data.id.value").value(customer.id.value))
            }

            test("should return validation error for invalid register customer request") {
                val command =
                    createTestRegisterCustomerCommand(
                        email = "invalid-email",
                        password = "short",
                        name = "",
                        nickname = "",
                    )

                mockMvc
                    .perform(
                        post("/api/v1/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)),
                    ).andExpect(status().isUnprocessableEntity)
                    .andExpect(jsonPath("$.status").value("ERROR"))
                    .andExpect(jsonPath("$.message").value("Validation failed"))
                    .andExpect(jsonPath("$.data").isMap)
                    .andExpect(jsonPath("$.data.email").value("Email should be valid"))
                    .andExpect(
                        jsonPath("$.data.password").value(
                            containsInAnyOrder(
                                "Password must be at least 8 characters long",
                                "Password must include upper, lower, number, special char and be at least 8 characters long",
                            ),
                        ),
                    ).andExpect(jsonPath("$.data.name").value("Name must not be blank"))
                    .andExpect(jsonPath("$.data.nickname").value("Nickname must not be blank"))
            }
        }

        context("PATCH /api/v1/customers/{customerId}") {
            test("should update a customer") {
                val customerId = "testId"
                val command =
                    UpdateCustomerInfoCommand(
                        name = "Updated Name",
                        nickname = null,
                        address = null,
                        phoneNumber = null,
                    )

                val updatedCustomer =
                    createTestCustomer(customerId).updateCustomerInfo(
                        newName = Customer.CustomerName(command.name!!),
                        newNickname = null,
                        newAddress = null,
                        newPhoneNumber = null,
                    )
                every { customerCommandService.updateCustomerInfo(any(), any()) } returns updatedCustomer

                mockMvc
                    .perform(
                        patch("/api/v1/customers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)),
                    ).andExpect(status().isOk)
                    .andExpect(jsonPath("$.data.id.value").value(updatedCustomer.id.value))
                    .andExpect(jsonPath("$.data.name.value").value(updatedCustomer.name.value))
            }
        }
    })
