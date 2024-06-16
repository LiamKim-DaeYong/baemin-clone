package com.demin.auth.application.port.outgoing.storeowner

import com.demin.auth.domain.StoreOwner

interface SaveStoreOwnerPort {
    fun save(storeOwner: StoreOwner): StoreOwner
}
