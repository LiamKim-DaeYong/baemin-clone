package com.demin.common.address

import jakarta.validation.constraints.NotBlank

data class AddressDto(
    @field:NotBlank(message = "Zipcode must not be blank")
    val zipCode: String,

    @field:NotBlank(message = "Address must not be blank")
    val address: String,

    @field:NotBlank(message = "Detail Address must not be blank")
    val detailAddress: String
) {
    companion object {
        fun fromEntity(entity: Address): AddressDto {
            return AddressDto(
                zipCode = entity.zipCode,
                address = entity.address,
                detailAddress = entity.detailAddress
            )
        }
    }

    fun toEntity(): Address {
        return Address(
            address = this.address,
            detailAddress = this.detailAddress,
            zipCode = this.zipCode
        )
    }
}