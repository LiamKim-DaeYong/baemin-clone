package com.demin.auth.mapper

import com.demin.auth.adapter.outgoing.persistence.customer.CustomerJpaEntity
import com.demin.auth.domain.Customer

fun Customer.toEntity(): CustomerJpaEntity =
    CustomerJpaEntity(
        id = id.value,
        name = name.value,
        nickname = nickname.value,
        address = address.value.toEntity(),
        phoneNumber = phoneNumber.value,
        grade = grade.value,
        userAccount = userAccount.toEntity(),
    )

fun CustomerJpaEntity.toDomain(): Customer = Customer.fromJpaEntity(this)
