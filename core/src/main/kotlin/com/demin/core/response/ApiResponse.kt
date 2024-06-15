package com.demin.core.response

data class ApiResponse<T>(
    val status: Status,
    val message: String?,
    val data: T?,
    val httpStatus: Int,
    val errorCode: String? = null,
) {
    companion object {
        fun <T> success(
            data: T,
            message: String? = null,
            httpStatus: Int = 200,
        ): ApiResponse<T> = ApiResponse(Status.SUCCESS, message, data, httpStatus)

        fun <T> fail(
            message: String? = null,
            data: T? = null,
            httpStatus: Int = 400,
        ): ApiResponse<T> = ApiResponse(Status.FAIL, message, data, httpStatus)

        fun <T> error(
            message: String,
            errorCode: String? = null,
            data: T? = null,
            httpStatus: Int = 500,
        ): ApiResponse<T> = ApiResponse(Status.ERROR, message, data, httpStatus, errorCode)
    }
}
