package com.demin.auth.domain

import com.demin.auth.adapter.outgoing.persistence.customer.CustomerJpaEntity
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
        ): Customer =
            Customer(
                id = CustomerId(userAccount.id.value),
                name = customerName,
                nickname = customerNickname,
                address = customerAddress,
                phoneNumber = customerPhoneNumber,
                grade = CustomerGrade(CustomerGrade.Grade.BRONZE),
                userAccount = userAccount,
            )

        fun fromJpaEntity(jpaEntity: CustomerJpaEntity): Customer =
            Customer(
                id = CustomerId(jpaEntity.id),
                name = CustomerName(jpaEntity.name),
                nickname = CustomerNickname(jpaEntity.nickname),
                address = CustomerAddress(jpaEntity.address.toAddressDto()),
                phoneNumber = CustomerPhoneNumber(jpaEntity.phoneNumber),
                grade = CustomerGrade(CustomerGrade.Grade.BRONZE),
                userAccount = UserAccount.fromJpaEntity(jpaEntity.userAccount),
            )
    }

    /**** Business Logic ****/
    fun updateCustomerInfo(
        newName: CustomerName?,
        newNickname: CustomerNickname?,
        newAddress: CustomerAddress?,
        newPhoneNumber: CustomerPhoneNumber?,
    ): Customer =
        Customer(
            id = this.id,
            name = newName ?: this.name,
            nickname = newNickname ?: this.nickname,
            address = newAddress ?: this.address,
            phoneNumber = newPhoneNumber ?: this.phoneNumber,
            grade = this.grade,
            userAccount = this.userAccount,
        )

    data class CustomerId(
        val value: String,
    )

    data class CustomerName(
        val value: String,
    )

    data class CustomerNickname(
        val value: String,
    )

    data class CustomerAddress(
        val value: AddressDto,
    )

    data class CustomerPhoneNumber(
        val value: String,
    )

    data class CustomerGrade(
        val value: Grade,
    ) {
        enum class Grade {
            BRONZE,
            SILVER,
            GOLD,
            PLATINUM,
        }
    }
}
