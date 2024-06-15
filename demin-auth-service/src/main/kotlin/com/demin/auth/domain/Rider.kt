package com.demin.auth.domain

import com.demin.core.address.AddressDto

class Rider private constructor(
    val id: RiderId,
    val name: RiderName,
    val address: RiderAddress,
    val deliveryArea: DeliveryArea,
    val deliveryMethod: DeliveryMethod,
    val userAccount: UserAccount,
) {
    companion object {
        fun create(
            riderName: RiderName,
            riderAddress: RiderAddress,
            deliveryArea: DeliveryArea,
            deliveryMethod: DeliveryMethod,
            userAccount: UserAccount,
        ): Rider {
            return Rider(
                id = RiderId(userAccount.id.value),
                name = riderName,
                address = riderAddress,
                deliveryArea = deliveryArea,
                deliveryMethod = deliveryMethod,
                userAccount = userAccount,
            )
        }
    }

    data class RiderId(val value: String)

    data class RiderName(val value: String)

    data class RiderAddress(val value: AddressDto)

    data class DeliveryArea(val value: Area) {
        enum class Area {
            SEOUL,
        }
    }

    data class DeliveryMethod(val value: Method) {
        enum class Method {
            BICYCLE,
            MOTORCYCLE,
            CAR,
            WALK,
            ELECTRIC_BICYCLE_PAS,
            ELECTRIC_BICYCLE_STROKE,
            KICK_BOARD,
        }
    }
}
