package com.demin.core.response

data class PageResponse<T>(
    val status: Status,
    val message: String?,
    val content: List<T>,
    val page: PageDetails,
    val httpStatus: Int,
) {
    data class PageDetails(
        val totalElements: Long,
        val totalPages: Int,
        val currentPage: Int,
        val size: Int,
    )

    companion object {
        fun <T> success(
            content: List<T>,
            pageDetails: PageDetails,
            message: String? = null,
            httpStatus: Int = 200,
        ): PageResponse<T> {
            return PageResponse(Status.SUCCESS, message, content, pageDetails, httpStatus)
        }

        fun <T> fail(
            message: String? = null,
            httpStatus: Int = 400,
        ): PageResponse<T> {
            return PageResponse(Status.FAIL, message, emptyList(), PageDetails(0, 0, 0, 0), httpStatus)
        }

        fun <T> error(
            message: String,
            httpStatus: Int = 500,
        ): PageResponse<T> {
            return PageResponse(Status.ERROR, message, emptyList(), PageDetails(0, 0, 0, 0), httpStatus)
        }
    }
}
