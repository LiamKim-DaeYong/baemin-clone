package com.demin.auth.application.port.incoming.customer

import com.demin.auth.application.port.incoming.customer.command.UpdateCustomerCommand
import com.demin.auth.domain.Customer
import com.demin.core.hexagonal.annotations.UseCase

@UseCase
interface UpdateCustomerUseCase {
    fun updateCustomer(command: UpdateCustomerCommand): Customer
}
