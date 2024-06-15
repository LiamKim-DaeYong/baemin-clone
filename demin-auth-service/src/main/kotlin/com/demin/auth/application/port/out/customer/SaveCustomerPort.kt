package com.demin.auth.application.port.out.customer

import com.demin.auth.domain.Customer

interface SaveCustomerPort {
    fun save(customer: Customer): Customer
}
