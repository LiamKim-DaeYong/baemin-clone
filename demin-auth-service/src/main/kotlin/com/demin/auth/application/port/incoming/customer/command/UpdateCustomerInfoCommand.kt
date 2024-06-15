package com.demin.auth.application.port.incoming.customer.command

import com.demin.core.address.AddressDto
import com.demin.core.util.RegexPatterns
import jakarta.validation.constraints.Pattern

data class UpdateCustomerInfoCommand(
    val customerId: String,

    val name: String?,

    val nickname: String?,

    val address: AddressDto?,

    @field:Pattern(
        regexp = RegexPatterns.PHONE_NUMBER,
        message = "{phone_number.invalid}",
    )
    val phoneNumber: String?,
)
