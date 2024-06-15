package com.demin.store.application.port.`in`.command

import com.demin.core.address.AddressDto
import com.demin.store.domain.OperatingHour
import com.demin.store.domain.Store.*
import com.demin.store.enums.HolidayType
import jakarta.validation.constraints.NotBlank

data class RegisterStoreCommand(
    @field:NotBlank(message = "Store Name must not be blank")
    val storeName: StoreName,
    val address: AddressDto,
    val operatingHours: List<OperatingHour>,
    val holidayType: HolidayType,
    val description: StoreDescription,
)
