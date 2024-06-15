package com.demin.auth.application.port.outgoing.customer

import com.demin.auth.domain.Customer

interface SaveCustomerPort {
    fun save(customer: Customer): Customer
}
