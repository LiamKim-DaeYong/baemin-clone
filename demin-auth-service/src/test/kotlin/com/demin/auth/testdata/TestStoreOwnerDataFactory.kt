package com.demin.auth.testdata

import com.demin.auth.application.port.incoming.storeowner.command.RegisterStoreOwnerCommand
import com.demin.auth.domain.StoreOwner
import com.demin.auth.domain.UserAccount
import com.demin.core.address.AddressDto

object TestStoreOwnerDataFactory {
    fun createTestStoreOwner(storeOwnerId: String) =
        StoreOwner.create(
            storeOwnerName = StoreOwner.StoreOwnerName("John Doe"),
            storeOwnerAddress = StoreOwner.StoreOwnerAddress(AddressDto("123", "Main St", "123-45")),
            storeOwnerPhoneNumber = StoreOwner.StoreOwnerPhoneNumber("010-1234-5678"),
            userAccount =
                UserAccount.create(
                    id = UserAccount.UserAccountId(storeOwnerId),
                    email = UserAccount.UserEmail("test@example.com"),
                    password = UserAccount.UserPassword("password"),
                    role = UserAccount.UserRole(UserAccount.UserRole.Role.STORE_OWNER),
                ),
        )

    fun createTestRegisterStoreOwnerCommand(
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
    ) = RegisterStoreOwnerCommand(
        email = email,
        password = password,
        name = name,
        address = address,
        phoneNumber = phoneNumber,
    )
}
