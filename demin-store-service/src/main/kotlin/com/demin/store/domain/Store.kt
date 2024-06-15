package com.demin.store.domain

import com.demin.core.address.AddressDto
import com.demin.store.enums.DayType
import com.demin.store.enums.HolidayType
import java.time.LocalDateTime
import java.time.LocalTime

data class Store private constructor(
    val storeId: StoreId,
    val storeName: StoreName,
    val storeAddress: AddressDto,
    val operatingHours: List<OperatingHour>,
    val holidayType: HolidayType,
    val storeDescription: StoreDescription,
) {
    companion object {
        fun generateStore(
            storeId: StoreId,
            storeName: StoreName,
            storeAddress: AddressDto,
            operatingHours: List<OperatingHour>,
            holidayType: HolidayType,
            storeDescription: StoreDescription,
            createdAt: LocalDateTime? = null,
            createdBy: String? = null,
            updatedAt: LocalDateTime? = null,
            updatedBy: String? = null,
        ) = Store(
            storeId = storeId,
            storeName = storeName,
            storeAddress = storeAddress,
            operatingHours = operatingHours,
            holidayType = holidayType,
            storeDescription = storeDescription,
        )
    }

    data class StoreId(val value: String)

    data class StoreName(val value: String)

    data class StoreDescription(val value: String)
}

data class OperatingHour(
    val day: DayType,
    val openTime: LocalTime,
    val closeTime: LocalTime,
)
