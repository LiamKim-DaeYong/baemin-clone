package com.demin.core.address

import jakarta.validation.constraints.NotBlank

data class AddressDto(
    @field:NotBlank(message = "{zipcode.not_blank}")
    val zipCode: String,

    @field:NotBlank(message = "{address.not_blank}")
    val address: String,

    @field:NotBlank(message = "{detail_address.not_blank}")
    val detailAddress: String,
) {
    fun toEntity(): AddressJpaEntity =
        AddressJpaEntity(
            address = this.address,
            detailAddress = this.detailAddress,
            zipCode = this.zipCode,
        )
}
