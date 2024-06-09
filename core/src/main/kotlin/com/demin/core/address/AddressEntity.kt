package com.demin.core.address

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class AddressEntity(
    @Column(nullable = false)
    val zipCode: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val detailAddress: String
) {
    fun toDto(): AddressDto {
        return AddressDto(
            zipCode = this.zipCode,
            address = this.address,
            detailAddress = this.detailAddress
        )
    }
}