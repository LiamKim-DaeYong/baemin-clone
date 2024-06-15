package com.demin.auth.domain

class Admin private constructor(
    val id: AdminId,
    val name: AdminName,
    val userAccount: UserAccount,
    val permissions: List<Permission>,
) {
    companion object {
        fun create(
            adminName: AdminName,
            userAccount: UserAccount,
        ): Admin {
            return Admin(
                id = AdminId(userAccount.id.value),
                name = adminName,
                userAccount = userAccount,
                permissions = listOf(),
            )
        }
    }

    data class AdminId(val value: String)

    data class AdminName(val value: String)

    data class Permission(val value: String)
}
