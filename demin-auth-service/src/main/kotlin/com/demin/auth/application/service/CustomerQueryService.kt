package com.demin.auth.application.service

import com.demin.auth.application.port.incoming.customer.FindCustomerUseCase
import com.demin.auth.application.port.outgoing.customer.FindCustomerPort
import com.demin.auth.domain.Customer
import com.demin.core.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CustomerQueryService(
    private val findCustomerPort: FindCustomerPort,
) : FindCustomerUseCase {
    override fun findCustomerById(customerId: String): Customer {
        // Find the Customer by ID
        return findCustomerPort.findById(customerId)
            ?: throw ResourceNotFoundException("Customer not found")
    }

    override fun findAllCustomers(): List<Customer> {
        // Find all Customers
        return findCustomerPort.findAll()
    }
}
