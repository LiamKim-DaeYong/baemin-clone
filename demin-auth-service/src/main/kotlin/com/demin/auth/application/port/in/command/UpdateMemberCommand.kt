package com.demin.auth.application.port.`in`.command

import com.demin.common.address.AddressDto
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateMemberCommand(
    val memberId: String,

    @field:Email(message = "Email should be valid")
    val email: String?,

    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    val password: String?,

    val name: String?,

    val nickname: String?,

    val address: AddressDto?,

    @field:Pattern(
        regexp = "^(\\+\\d{1,3}[- ]?)?(010|011|016|017|018|019)([- ]?\\d{3,4})[- ]?\\d{4}\$",
        message = "Phone number should be valid"
    )
    val phoneNumber: String?,
)