package com.demin.auth.adapter.out.persistence.customer

import com.demin.auth.adapter.out.persistence.useraccount.UserAccountJpaRepository
import com.demin.auth.application.port.out.customer.FindCustomerPort
import com.demin.auth.application.port.out.customer.SaveCustomerPort
import com.demin.auth.application.port.out.customer.UpdateCustomerPort
import com.demin.auth.domain.Customer
import com.demin.core.hexagonal.annotations.PersistenceAdapter

@PersistenceAdapter
class CustomerPersistenceAdapter(
    private val userAccountJpaRepository: UserAccountJpaRepository,
) : SaveCustomerPort, UpdateCustomerPort, FindCustomerPort {
    override fun save(customer: Customer): Customer {
        TODO("Not yet implemented")
    }

    override fun update(customer: Customer): Customer {
        TODO("Not yet implemented")
    }

    override fun findById(customerId: String): Customer? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Customer> {
        TODO("Not yet implemented")
    }

//    override fun save(member: UserAccount): UserAccount {
//        val memberEntity = member.toEntity()
//        val savedEntity = userAccountJpaRepository.save(memberEntity)
//        return savedEntity.toMember()
//    }
//
//    override fun update(member: UserAccount): UserAccount {
//        val memberEntity = member.toEntity()
//        val updatedEntity = userAccountJpaRepository.save(memberEntity)
//        return updatedEntity.toMember()
//    }
//
//    override fun findById(customerId: String): UserAccount? {
//        return userAccountJpaRepository.findById(customerId).orElse(null)?.toMember()
//    }
//
//    override fun findAll(): List<UserAccount> {
//        return userAccountJpaRepository.findAll().map { it.toMember() }
//    }
}
