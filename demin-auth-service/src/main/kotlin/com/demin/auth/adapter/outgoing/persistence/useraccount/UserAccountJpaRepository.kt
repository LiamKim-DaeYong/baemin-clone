package com.demin.auth.adapter.outgoing.persistence.useraccount

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountJpaRepository : JpaRepository<UserAccountJpaEntity, String>
