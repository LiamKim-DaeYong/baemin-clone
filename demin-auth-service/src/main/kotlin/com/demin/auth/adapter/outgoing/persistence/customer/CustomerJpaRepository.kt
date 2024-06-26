package com.demin.auth.adapter.outgoing.persistence.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerJpaRepository : JpaRepository<CustomerJpaEntity, String>
