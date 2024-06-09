package com.demin.auth.application.port.`in`.command

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterMemberCommand(
    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Email should be valid")
    val email: String,

    @field:NotBlank(message = "Password must not be blank")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,

    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    @field:NotBlank(message = "Nickname must not be blank")
    val nickname: String,

    @field:NotBlank(message = "Address must not be blank")
    val address: String,

    @field:NotBlank(message = "Phone number must not be blank")
    @field:Pattern(regexp = "^\\d{10,15}\$", message = "Phone number should be valid")
    val phoneNumber: String
)