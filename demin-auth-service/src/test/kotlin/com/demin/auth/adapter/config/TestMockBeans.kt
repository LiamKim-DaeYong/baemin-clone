package com.demin.auth.adapter.config

import com.demin.auth.adapter.outgoing.persistence.customer.CustomerJpaRepository
import com.demin.auth.adapter.outgoing.persistence.storeowner.StoreOwnerJpaRepository
import com.demin.auth.adapter.outgoing.persistence.useraccount.UserAccountJpaRepository
import com.ninjasquad.springmockk.MockkBean
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext

@TestConfiguration
class TestMockBeans {
    @MockkBean
    lateinit var customerJpaRepository: CustomerJpaRepository

    @MockkBean
    lateinit var storeOwnerJpaRepository: StoreOwnerJpaRepository

    @MockkBean
    lateinit var userAccountJpaRepository: UserAccountJpaRepository

    @MockkBean
    lateinit var jpaMetamodelMappingContext: JpaMetamodelMappingContext
}
