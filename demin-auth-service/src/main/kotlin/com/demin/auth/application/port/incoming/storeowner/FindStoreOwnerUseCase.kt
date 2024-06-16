package com.demin.auth.application.port.incoming.storeowner

import com.demin.auth.domain.StoreOwner
import com.demin.core.hexagonal.annotations.UseCase

@UseCase
interface FindStoreOwnerUseCase {
    fun findStoreOwnerById(storeOwnerId: String): StoreOwner

    fun findAllStoreOwners(): List<StoreOwner>
}
