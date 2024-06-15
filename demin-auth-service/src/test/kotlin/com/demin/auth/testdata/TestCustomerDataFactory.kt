package com.demin.auth.testdata

import com.demin.auth.application.port.incoming.customer.command.RegisterCustomerCommand
import com.demin.auth.domain.Customer
import com.demin.auth.domain.UserAccount
import com.demin.core.address.AddressDto

object TestCustomerDataFactory {
    fun createTestCustomer(customerId: String): Customer =
        Customer.create(
            customerName = Customer.CustomerName("John Doe"),
            customerNickname = Customer.CustomerNickname("johndoe"),
            customerAddress = Customer.CustomerAddress(AddressDto("123", "Main St", "123-45")),
            customerPhoneNumber = Customer.CustomerPhoneNumber("010-1234-5678"),
            userAccount =
                UserAccount.create(
                    id = UserAccount.UserAccountId(customerId),
                    email = UserAccount.UserEmail("test@example.com"),
                    password = UserAccount.UserPassword("password"),
                    role = UserAccount.UserRole(UserAccount.UserRole.Role.CUSTOMER),
                ),
        )

    fun createTestRegisterCustomerCommand(
        email: String = "test@example.com",
        password: String = "Password123!!",
        name: String = "John Doe",
        address: AddressDto =
            AddressDto(
                zipCode = "123",
                address = "Main St",
                detailAddress = "123-45",
            ),
        phoneNumber: String = "010-1234-5678",
        nickname: String = "johndoe",
    ) = RegisterCustomerCommand(
        email = email,
        password = password,
        name = name,
        address = address,
        phoneNumber = phoneNumber,
        nickname = nickname,
    )
}
