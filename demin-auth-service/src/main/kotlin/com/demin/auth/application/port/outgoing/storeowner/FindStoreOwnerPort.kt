package com.demin.auth.application.port.outgoing.storeowner

import com.demin.auth.domain.StoreOwner

interface FindStoreOwnerPort {
    fun findById(storeOwnerId: String): StoreOwner?

    fun findAll(): List<StoreOwner>
}
