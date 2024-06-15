package com.demin.auth.adapter.outgoing.persistence.customer

import com.demin.auth.application.port.outgoing.customer.FindCustomerPort
import com.demin.auth.application.port.outgoing.customer.SaveCustomerPort
import com.demin.auth.domain.Customer
import com.demin.auth.mapper.toDomain
import com.demin.auth.mapper.toEntity
import com.demin.core.hexagonal.annotations.PersistenceAdapter

@PersistenceAdapter
class CustomerPersistenceAdapter(
    private val customerJpaRepository: CustomerJpaRepository,
) : SaveCustomerPort,
    FindCustomerPort {
    override fun save(customer: Customer): Customer {
        val customerJpaEntity = customer.toEntity()
        val savedEntity = customerJpaRepository.save(customerJpaEntity)
        return savedEntity.toDomain()
    }

    override fun findById(customerId: String): Customer? {
        val customerJpaEntity = customerJpaRepository.findById(customerId).orElse(null)
        return customerJpaEntity?.toDomain()
    }

    override fun findAll(): List<Customer> = customerJpaRepository.findAll().map { it.toDomain() }
}
