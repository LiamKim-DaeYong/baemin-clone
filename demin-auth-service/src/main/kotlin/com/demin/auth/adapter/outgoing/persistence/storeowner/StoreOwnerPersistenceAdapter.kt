package com.demin.auth.adapter.outgoing.persistence.storeowner

import com.demin.auth.application.port.outgoing.storeowner.FindStoreOwnerPort
import com.demin.auth.application.port.outgoing.storeowner.SaveStoreOwnerPort
import com.demin.auth.domain.StoreOwner
import com.demin.auth.mapper.toDomain
import com.demin.auth.mapper.toEntity
import com.demin.core.hexagonal.annotations.PersistenceAdapter

@PersistenceAdapter
class StoreOwnerPersistenceAdapter(
    private val storeOwnerJpaRepository: StoreOwnerJpaRepository,
) : SaveStoreOwnerPort,
    FindStoreOwnerPort {
    override fun save(storeOwner: StoreOwner): StoreOwner {
        val storeOwnerJpaEntity = storeOwner.toEntity()
        val savedEntity = storeOwnerJpaRepository.save(storeOwnerJpaEntity)
        return savedEntity.toDomain()
    }

    override fun findById(storeOwnerId: String): StoreOwner? {
        val storeOwnerJpaEntity = storeOwnerJpaRepository.findById(storeOwnerId).orElse(null)
        return storeOwnerJpaEntity?.toDomain()
    }

    override fun findAll(): List<StoreOwner> = storeOwnerJpaRepository.findAll().map { it.toDomain() }
}
