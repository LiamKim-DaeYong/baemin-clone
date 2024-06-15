package com.demin.auth.application.port.incoming.customer

import com.demin.auth.application.port.incoming.customer.command.RegisterCustomerCommand
import com.demin.auth.domain.Customer
import com.demin.core.hexagonal.annotations.UseCase

@UseCase
interface RegisterCustomerUseCase {
    fun registerCustomer(command: RegisterCustomerCommand): Customer
}
