package com.demin.auth.adapter.incoming.web

import com.demin.auth.application.port.incoming.storeowner.FindStoreOwnerUseCase
import com.demin.auth.application.port.incoming.storeowner.RegisterStoreOwnerUseCase
import com.demin.auth.application.port.incoming.storeowner.UpdateStoreOwnerUseCase
import com.demin.auth.application.port.incoming.storeowner.command.RegisterStoreOwnerCommand
import com.demin.auth.application.port.incoming.storeowner.command.UpdateStoreOwnerInfoCommand
import com.demin.auth.domain.StoreOwner
import com.demin.core.hexagonal.annotations.WebAdapter
import com.demin.core.response.ApiResponse
import com.demin.core.util.UriUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@WebAdapter
@RequestMapping("/api/v1/store-owners")
class StoreOwnerController(
    private val findStoreOwnerUseCase: FindStoreOwnerUseCase,
    private val registerStoreOwnerUseCase: RegisterStoreOwnerUseCase,
    private val updateStoreOwnerUseCase: UpdateStoreOwnerUseCase,
) {
    @GetMapping
    fun findAllStoreOwners(): ResponseEntity<ApiResponse<List<StoreOwner>>> {
        val storeOwners = findStoreOwnerUseCase.findAllStoreOwners()
        return ResponseEntity.ok(ApiResponse.success(storeOwners))
    }

    @GetMapping("/{storeOwnerId}")
    fun findStoreOwnerById(
        @PathVariable storeOwnerId: String,
    ): ResponseEntity<ApiResponse<StoreOwner>> {
        val storeOwner = findStoreOwnerUseCase.findStoreOwnerById(storeOwnerId)
        return ResponseEntity.ok(ApiResponse.success(storeOwner))
    }

    @PostMapping
    fun registerStoreOwner(
        @RequestBody @Valid command: RegisterStoreOwnerCommand,
    ): ResponseEntity<ApiResponse<StoreOwner>> {
        val storeOwner = registerStoreOwnerUseCase.registerStoreOwner(command)
        val location = UriUtil.createLocationUri("/{storeOwnerId}", storeOwner.id.value)

        return ResponseEntity.created(location).body(ApiResponse.success(storeOwner))
    }

    @PatchMapping("/{storeOwnerId}")
    fun updateStoreOwnerInfo(
        @PathVariable storeOwnerId: String,
        @RequestBody @Valid command: UpdateStoreOwnerInfoCommand,
    ): ResponseEntity<ApiResponse<StoreOwner>> {
        val updatedStoreOwner = updateStoreOwnerUseCase.updateStoreOwnerInfo(storeOwnerId, command)
        return ResponseEntity.ok(ApiResponse.success(updatedStoreOwner))
    }
}
