package com.demin.auth.application.port.`in`.command

data class UpdateMemberCommand(
    val memberId: String,
    val email: String?,
    val password: String?,
    val name: String?,
    val nickname: String?,
    val address: String?,
    val phoneNumber: String?,
)