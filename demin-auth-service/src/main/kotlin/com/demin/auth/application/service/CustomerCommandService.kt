package com.demin.auth.application.service

import com.demin.auth.application.port.incoming.customer.RegisterCustomerUseCase
import com.demin.auth.application.port.incoming.customer.UpdateCustomerUseCase
import com.demin.auth.application.port.incoming.customer.command.RegisterCustomerCommand
import com.demin.auth.application.port.incoming.customer.command.UpdateCustomerCommand
import com.demin.auth.application.port.out.customer.SaveCustomerPort
import com.demin.auth.application.port.out.customer.UpdateCustomerPort
import com.demin.auth.domain.Customer
import com.demin.core.util.SnowflakeIdGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerCommandService(
    private val saveCustomerPort: SaveCustomerPort,
    private val updateCustomerPort: UpdateCustomerPort,
    private val passwordEncoder: PasswordEncoder,
    private val snowflakeIdGenerator: SnowflakeIdGenerator,
) : RegisterCustomerUseCase, UpdateCustomerUseCase {
    override fun registerCustomer(command: RegisterCustomerCommand): Customer {
        TODO("Not yet implemented")
    }

    override fun updateCustomer(command: UpdateCustomerCommand): Customer {
        TODO("Not yet implemented")
    }
}
