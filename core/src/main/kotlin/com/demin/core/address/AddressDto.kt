package com.demin.core.address

import jakarta.validation.constraints.NotBlank

data class AddressDto(
    @field:NotBlank(message = "Zipcode must not be blank")
    val zipCode: String,

    @field:NotBlank(message = "Address must not be blank")
    val address: String,

    @field:NotBlank(message = "Detail Address must not be blank")
    val detailAddress: String
) {
    fun toEntity(): AddressEntity {
        return AddressEntity(
            address = this.address,
            detailAddress = this.detailAddress,
            zipCode = this.zipCode
        )
    }
}