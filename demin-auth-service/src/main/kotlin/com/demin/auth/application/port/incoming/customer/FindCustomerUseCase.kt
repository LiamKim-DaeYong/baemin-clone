package com.demin.auth.application.port.incoming.customer

import com.demin.auth.domain.Customer
import com.demin.core.hexagonal.annotations.UseCase

@UseCase
interface FindCustomerUseCase {
    fun findCustomerById(customerId: String): Customer

    fun findAllCustomers(): List<Customer>
}
