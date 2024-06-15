package com.demin.auth.application.service

import com.demin.auth.application.port.incoming.customer.RegisterCustomerUseCase
import com.demin.auth.application.port.incoming.customer.UpdateCustomerUseCase
import com.demin.auth.application.port.incoming.customer.command.RegisterCustomerCommand
import com.demin.auth.application.port.incoming.customer.command.UpdateCustomerInfoCommand
import com.demin.auth.application.port.outgoing.customer.FindCustomerPort
import com.demin.auth.application.port.outgoing.customer.SaveCustomerPort
import com.demin.auth.domain.Customer
import com.demin.auth.domain.UserAccount
import com.demin.core.exception.ResourceNotFoundException
import com.demin.core.util.SnowflakeIdGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerCommandService(
    private val saveCustomerPort: SaveCustomerPort,
    private val findCustomerPort: FindCustomerPort,
    private val passwordEncoder: PasswordEncoder,
    private val snowflakeIdGenerator: SnowflakeIdGenerator,
) : RegisterCustomerUseCase,
    UpdateCustomerUseCase {
    override fun registerCustomer(command: RegisterCustomerCommand): Customer {
        // Encode the password
        val encodedPassword = passwordEncoder.encode(command.password)

        // Create UserAccount
        val userAccount =
            UserAccount.create(
                id = UserAccount.UserAccountId(snowflakeIdGenerator.nextId()),
                email = UserAccount.UserEmail(command.email),
                password = UserAccount.UserPassword(encodedPassword),
                role = UserAccount.UserRole(UserAccount.UserRole.Role.CUSTOMER),
            )

        // Create Customer
        val customer =
            Customer.create(
                customerNickname = Customer.CustomerNickname(command.nickname),
                customerName = Customer.CustomerName(command.name),
                customerAddress = Customer.CustomerAddress(command.address),
                customerPhoneNumber = Customer.CustomerPhoneNumber(command.phoneNumber),
                userAccount = userAccount,
            )

        // Save and return the Customer
        return saveCustomerPort.save(customer)
    }

    override fun updateCustomerInfo(command: UpdateCustomerInfoCommand): Customer {
        // Find the Customer
        val customer =
            findCustomerPort.findById(command.customerId)
                ?: throw ResourceNotFoundException("Customer not found")

        // Update Customer information
        val updatedCustomer =
            customer.updateCustomerInfo(
                newName = command.name?.let { Customer.CustomerName(it) },
                newNickname = command.nickname?.let { Customer.CustomerNickname(it) },
                newAddress = command.address?.let { Customer.CustomerAddress(it) },
                newPhoneNumber = command.phoneNumber?.let { Customer.CustomerPhoneNumber(it) },
            )

        // Save and return the updated Customer
        return saveCustomerPort.save(updatedCustomer)
    }
}
