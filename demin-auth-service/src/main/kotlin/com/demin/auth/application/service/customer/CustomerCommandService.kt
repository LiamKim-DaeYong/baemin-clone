package com.demin.auth.application.service.customer

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
        with(command) {
            // Encode the password
            val encodedPassword = passwordEncoder.encode(password)

            // Create UserAccount
            val userAccount =
                UserAccount.create(
                    id = UserAccount.UserAccountId(snowflakeIdGenerator.nextId()),
                    email = UserAccount.UserEmail(email),
                    password = UserAccount.UserPassword(encodedPassword),
                    role = UserAccount.UserRole(UserAccount.UserRole.Role.CUSTOMER),
                )

            // Create Customer
            val customer =
                Customer.create(
                    customerNickname = Customer.CustomerNickname(nickname),
                    customerName = Customer.CustomerName(name),
                    customerAddress = Customer.CustomerAddress(address),
                    customerPhoneNumber = Customer.CustomerPhoneNumber(phoneNumber),
                    userAccount = userAccount,
                )

            // Save and return the Customer
            return saveCustomerPort.save(customer)
        }
    }

    override fun updateCustomerInfo(
        customerId: String,
        command: UpdateCustomerInfoCommand,
    ): Customer {
        with(command) {
            // Find the Customer
            val customer =
                findCustomerPort.findById(customerId)
                    ?: throw ResourceNotFoundException("Customer with ID $customerId not found")

            // Update Customer information
            val updatedCustomer =
                customer.updateCustomerInfo(
                    newName = name?.let { Customer.CustomerName(it) },
                    newNickname = nickname?.let { Customer.CustomerNickname(it) },
                    newAddress = address?.let { Customer.CustomerAddress(it) },
                    newPhoneNumber = phoneNumber?.let { Customer.CustomerPhoneNumber(it) },
                )

            // Save and return the updated Customer
            return saveCustomerPort.save(updatedCustomer)
        }
    }
}
