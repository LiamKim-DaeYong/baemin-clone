package com.demin.common.exception

import org.springframework.http.HttpStatus

// Base Custom Exception
open class CustomException(
    val errorCode: String,
    message: String,
    val status: HttpStatus
) : RuntimeException(message)

// Validation Exception
class ValidationException(
    message: String,
    errorCode: String = "VALIDATION_ERROR",
    status: HttpStatus = HttpStatus.UNPROCESSABLE_ENTITY
) : CustomException(errorCode, message, status)

// Resource Not Found Exception
class ResourceNotFoundException(
    message: String,
    errorCode: String = "RESOURCE_NOT_FOUND",
    status: HttpStatus = HttpStatus.NOT_FOUND
) : CustomException(errorCode, message, status)

// Database Exception
class DatabaseException(
    message: String,
    errorCode: String = "DATABASE_ERROR",
    status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : CustomException(errorCode, message, status)

// Authentication Exception
class AuthenticationException(
    message: String,
    errorCode: String = "AUTHENTICATION_ERROR",
    status: HttpStatus = HttpStatus.UNAUTHORIZED
) : CustomException(errorCode, message, status)

// Authorization Exception
class AuthorizationException(
    message: String,
    errorCode: String = "AUTHORIZATION_ERROR",
    status: HttpStatus = HttpStatus.FORBIDDEN
) : CustomException(errorCode, message, status)