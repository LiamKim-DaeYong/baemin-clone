package com.demin.core.validator.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NotBlankIfPresentValidator : ConstraintValidator<NotBlankIfPresent, String?> {
    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext,
    ): Boolean = value?.isNotBlank() ?: true
}
