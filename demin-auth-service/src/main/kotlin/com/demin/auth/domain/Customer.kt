package com.demin.auth.domain

import com.demin.auth.domain.Customer.CustomerGrade.Grade.BRONZE
import com.demin.core.address.AddressDto

class Customer private constructor(
    val id: CustomerId,
    val name: CustomerName,
    val nickname: CustomerNickname,
    val address: CustomerAddress,
    val phoneNumber: CustomerPhoneNumber,
    val grade: CustomerGrade,
    val userAccount: UserAccount,
) {
    companion object {
        fun create(
            customerName: CustomerName,
            customerNickname: CustomerNickname,
            customerAddress: CustomerAddress,
            customerPhoneNumber: CustomerPhoneNumber,
            userAccount: UserAccount,
        ): Customer {
            return Customer(
                id = CustomerId(userAccount.id.value),
                name = customerName,
                nickname = customerNickname,
                address = customerAddress,
                phoneNumber = customerPhoneNumber,
                grade = CustomerGrade(BRONZE),
                userAccount = userAccount,
            )
        }
    }

    data class CustomerId(val value: String)

    data class CustomerName(val value: String)

    data class CustomerNickname(val value: String)

    data class CustomerAddress(val value: AddressDto)

    data class CustomerPhoneNumber(val value: String)

    data class CustomerGrade(val value: Grade) {
        enum class Grade {
            BRONZE,
            SILVER,
            GOLD,
            PLATINUM,
        }
    }
}
