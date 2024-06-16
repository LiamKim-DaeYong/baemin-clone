package com.demin.auth.adapter.outgoing.persistence.storeowner

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreOwnerJpaRepository : JpaRepository<StoreOwnerJpaEntity, String>
