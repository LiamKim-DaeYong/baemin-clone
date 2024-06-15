package com.demin.core.exception

import com.demin.core.response.ApiResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler(private val messageSource: MessageSource) {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<ApiResponse<String>> {
        val locale = LocaleContextHolder.getLocale()
        val message =
            messageSource.getMessage(ex.errorCode, null, null, locale)
                ?: ex.message ?: "An error occurred"

        val response =
            ApiResponse.error<String>(
                message = message,
                errorCode = ex.errorCode,
                httpStatus = ex.status.value(),
            )

        return ResponseEntity.status(ex.status).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: WebRequest,
    ): ResponseEntity<ApiResponse<Map<String, String?>>> {
        val errors =
            ex.bindingResult.fieldErrors.associateBy(
                { it.field },
                { messageSource.getMessage(it, LocaleContextHolder.getLocale()) },
            )

        val response =
            ApiResponse.error<Map<String, String?>>(
                message = "Validation failed",
                data = errors,
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            )

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiResponse<String>> {
        val response = ApiResponse.error<String>("Internal server error")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}
