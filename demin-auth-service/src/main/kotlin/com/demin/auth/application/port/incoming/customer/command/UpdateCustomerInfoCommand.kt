package com.demin.auth.application.port.incoming.customer.command

import com.demin.core.address.AddressDto
import com.demin.core.util.RegexPatterns
import com.demin.core.validator.annotation.NotBlankIfPresent
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern

data class UpdateCustomerInfoCommand(
    @NotBlankIfPresent("{name.not_blank}")
    val name: String?,

    @NotBlankIfPresent("{nickname.not_blank}")
    val nickname: String?,

    @field:Valid
    val address: AddressDto?,

    @NotBlankIfPresent("{phone_number.not_blank}")
    @field:Pattern(
        regexp = RegexPatterns.PHONE_NUMBER,
        message = "{phone_number.invalid}",
    )
    val phoneNumber: String?,
)
