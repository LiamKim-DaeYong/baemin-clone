package com.demin.auth.domain

import com.demin.auth.adapter.outgoing.persistence.storeowner.StoreOwnerJpaEntity
import com.demin.core.address.AddressDto

class StoreOwner private constructor(
    val name: StoreOwnerName,
    val address: StoreOwnerAddress,
    val phoneNumber: StoreOwnerPhoneNumber,
    val userAccount: UserAccount,
) {
    val id: StoreOwnerId
        get() = StoreOwnerId(userAccount.id.value)

    companion object {
        fun create(
            storeOwnerName: StoreOwnerName,
            storeOwnerAddress: StoreOwnerAddress,
            storeOwnerPhoneNumber: StoreOwnerPhoneNumber,
            userAccount: UserAccount,
        ) = StoreOwner(
            name = storeOwnerName,
            address = storeOwnerAddress,
            phoneNumber = storeOwnerPhoneNumber,
            userAccount = userAccount,
        )

        fun fromJpaEntity(jpaEntity: StoreOwnerJpaEntity) =
            StoreOwner(
                name = StoreOwnerName(jpaEntity.name),
                address = StoreOwnerAddress(jpaEntity.address.toAddressDto()),
                phoneNumber = StoreOwnerPhoneNumber(jpaEntity.phoneNumber),
                userAccount = UserAccount.fromJpaEntity(jpaEntity.userAccount),
            )
    }

    /**** Business Logic ****/
    fun updateStoreOwnerInfo(
        newName: StoreOwnerName?,
        newAddress: StoreOwnerAddress?,
        newPhoneNumber: StoreOwnerPhoneNumber?,
    ) = StoreOwner(
        name = newName ?: this.name,
        address = newAddress ?: this.address,
        phoneNumber = newPhoneNumber ?: this.phoneNumber,
        userAccount = this.userAccount,
    )

    data class StoreOwnerId(
        val value: String,
    )

    data class StoreOwnerName(
        val value: String,
    )

    data class StoreOwnerAddress(
        val value: AddressDto,
    )

    data class StoreOwnerPhoneNumber(
        val value: String,
    )
}
