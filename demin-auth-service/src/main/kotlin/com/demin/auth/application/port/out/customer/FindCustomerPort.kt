package com.demin.auth.application.port.out.customer

import com.demin.auth.domain.Customer

interface FindCustomerPort {
    fun findById(customerId: String): Customer?

    fun findAll(): List<Customer>
}
