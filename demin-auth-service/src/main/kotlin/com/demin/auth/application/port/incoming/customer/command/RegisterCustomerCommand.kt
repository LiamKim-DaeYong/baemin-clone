package com.demin.auth.application.port.incoming.customer.command

import com.demin.core.address.AddressDto
import com.demin.core.util.RegexPatterns
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterCustomerCommand(
    @field:NotBlank(message = "{email.not_blank}")
    @field:Email(message = "{email.invalid}")
    val email: String,

    @field:NotBlank(message = "{password.not_blank}")
    @field:Size(min = 8, message = "{password.size}")
    @field:Pattern(
        regexp = RegexPatterns.PASSWORD,
        message = "{password.pattern}",
    )
    val password: String,

    @field:NotBlank(message = "{name.not_blank}")
    val name: String,

    @field:NotBlank(message = "{nickname.not_blank}")
    val nickname: String,

    val address: AddressDto,

    @field:NotBlank(message = "{phone_number.not_blank}")
    @field:Pattern(
        regexp = RegexPatterns.PHONE_NUMBER,
        message = "{phone_number.invalid}",
    )
    val phoneNumber: String,
)
