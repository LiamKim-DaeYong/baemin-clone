package com.demin.auth.application.port.out.customer

import com.demin.auth.domain.Customer

interface UpdateCustomerPort {
    fun update(customer: Customer): Customer
}
