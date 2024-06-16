package com.demin.auth.application.service.store

import com.demin.auth.application.port.incoming.storeowner.FindStoreOwnerUseCase
import com.demin.auth.application.port.outgoing.storeowner.FindStoreOwnerPort
import com.demin.auth.domain.StoreOwner
import com.demin.core.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreOwnerQueryService(
    private val findStoreOwnerPort: FindStoreOwnerPort,
) : FindStoreOwnerUseCase {
    override fun findStoreOwnerById(storeOwnerId: String): StoreOwner {
        // Find the Store Owner by ID
        return findStoreOwnerPort.findById(storeOwnerId)
            ?: throw ResourceNotFoundException("Store owner with ID $storeOwnerId not found")
    }

    override fun findAllStoreOwners(): List<StoreOwner> {
        // Find all Store Owners
        return findStoreOwnerPort.findAll()
    }
}
