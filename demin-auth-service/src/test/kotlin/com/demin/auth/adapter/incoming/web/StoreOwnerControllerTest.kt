package com.demin.auth.adapter.incoming.web

import com.demin.auth.application.port.incoming.storeowner.command.UpdateStoreOwnerInfoCommand
import com.demin.auth.application.service.store.StoreOwnerCommandService
import com.demin.auth.application.service.store.StoreOwnerQueryService
import com.demin.auth.config.SecurityConfig
import com.demin.auth.domain.StoreOwner
import com.demin.auth.testdata.TestStoreOwnerDataFactory.createTestRegisterStoreOwnerCommand
import com.demin.auth.testdata.TestStoreOwnerDataFactory.createTestStoreOwner
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
    controllers = [StoreOwnerController::class],
    excludeFilters = [
        ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [SecurityConfig::class]),
    ],
)
class StoreOwnerControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean private val storeOwnerQueryService: StoreOwnerQueryService,
    @MockkBean private val storeOwnerCommandStoreOwner: StoreOwnerCommandService,
) : FunSpec({

        context("GET /api/v1/store-owners") {
            test("should return all store owners") {
                val storeOwners =
                    listOf(
                        createTestStoreOwner("storeOwnerId1"),
                        createTestStoreOwner("storeOwnerId2"),
                        createTestStoreOwner("storeOwnerId3"),
                    )

                every { storeOwnerQueryService.findAllStoreOwners() } returns storeOwners

                mockMvc
                    .perform(get("/api/v1/store-owners"))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data").isArray)
                    .andExpect(jsonPath("$.data[0].id.value").value(storeOwners[0].id.value))
            }

            test("should return a store owner by id") {
                val storeOwner = createTestStoreOwner("storeOwnerId1")
                every { storeOwnerQueryService.findStoreOwnerById("1") } returns storeOwner

                mockMvc
                    .perform(get("/api/v1/store-owners/1"))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data.id.value").value(storeOwner.id.value))
            }
        }

        context("POST /api/v1/store-owners") {
            test("should register a new store owner") {
                val command = createTestRegisterStoreOwnerCommand()

                val storeOwner = createTestStoreOwner("123456")
                every { storeOwnerCommandStoreOwner.registerStoreOwner(any()) } returns storeOwner

                mockMvc
                    .perform(
                        post("/api/v1/store-owners")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)),
                    ).andExpect(status().isCreated)
                    .andExpect(header().exists("Location"))
                    .andExpect(jsonPath("$.data.id.value").value(storeOwner.id.value))
            }

            test("should return validation error for invalid register store owner request") {
                val command =
                    createTestRegisterStoreOwnerCommand(
                        email = "invalid-email",
                        password = "short",
                        name = "",
                    )

                mockMvc
                    .perform(
                        post("/api/v1/store-owners")
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
            }
        }

        context("PATCH /api/v1/StoreOwners/{StoreOwnerId}") {
            test("should update a StoreOwner") {
                val storeOwnerId = "testId"
                val command =
                    UpdateStoreOwnerInfoCommand(
                        name = "Updated Name",
                        address = null,
                        phoneNumber = null,
                    )

                val updatedStoreOwner =
                    createTestStoreOwner(storeOwnerId).updateStoreOwnerInfo(
                        newName = StoreOwner.StoreOwnerName(command.name!!),
                        newAddress = null,
                        newPhoneNumber = null,
                    )
                every { storeOwnerCommandStoreOwner.updateStoreOwnerInfo(any(), any()) } returns updatedStoreOwner

                mockMvc
                    .perform(
                        patch("/api/v1/store-owners/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(command)),
                    ).andExpect(status().isOk)
                    .andExpect(jsonPath("$.data.id.value").value(updatedStoreOwner.id.value))
                    .andExpect(jsonPath("$.data.name.value").value(updatedStoreOwner.name.value))
            }
        }
    })
