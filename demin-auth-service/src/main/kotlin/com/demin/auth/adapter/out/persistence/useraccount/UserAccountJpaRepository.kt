package com.demin.auth.adapter.out.persistence.useraccount

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountJpaRepository : JpaRepository<UserAccountJpaEntity, String>
