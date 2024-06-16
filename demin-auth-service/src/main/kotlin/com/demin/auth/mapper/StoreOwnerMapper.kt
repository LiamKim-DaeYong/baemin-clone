package com.demin.auth.mapper

import com.demin.auth.adapter.outgoing.persistence.storeowner.StoreOwnerJpaEntity
import com.demin.auth.domain.StoreOwner

fun StoreOwner.toEntity() =
    StoreOwnerJpaEntity(
        id = id.value,
        name = name.value,
        address = address.value.toEntity(),
        phoneNumber = phoneNumber.value,
        userAccount = userAccount.toEntity(),
    )

fun StoreOwnerJpaEntity.toDomain() = StoreOwner.fromJpaEntity(this)
