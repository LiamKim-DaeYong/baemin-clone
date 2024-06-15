package com.demin.auth.application.service

import com.demin.auth.application.port.incoming.customer.FindCustomerUseCase
import com.demin.auth.application.port.out.customer.FindCustomerPort
import com.demin.auth.domain.Customer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CustomerQueryService(
    private val findCustomerPort: FindCustomerPort,
) : FindCustomerUseCase {
    override fun findCustomerById(customerId: String): Customer {
        TODO("Not yet implemented")
    }

    override fun findAllCustomers(): List<Customer> {
        TODO("Not yet implemented")
    }
}
