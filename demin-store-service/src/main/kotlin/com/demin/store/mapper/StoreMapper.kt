package com.demin.store.mapper

import com.demin.store.adapter.out.persistence.OperatingHourEmbeddable
import com.demin.store.adapter.out.persistence.StoreEntity
import com.demin.store.domain.OperatingHour
import com.demin.store.domain.Store
import com.demin.store.domain.Store.*
import com.demin.store.domain.Store.Companion.generateStore
import com.demin.store.enums.DayType
import com.demin.store.enums.HolidayType

fun StoreEntity.toStore(): Store =
    generateStore(
        storeId = StoreId(this.id),
        storeName = StoreName(this.storeName),
        storeAddress = this.storeAddress.toDto(),
        operatingHours =
            operatingHours.map {
                OperatingHour(
                    day = DayType(it.day),
                    openTime = it.openTime,
                    closeTime = it.closeTime,
                )
            },
        holidayType = HolidayType(this.holidayType),
        storeDescription = StoreDescription(this.storeDescription),
    )

fun Store.toEntity(): StoreEntity =
    StoreEntity(
        id = this.storeId.value,
        storeName = this.storeName.value,
        storeAddress = this.storeAddress.toEntity(),
        operatingHours =
            this.operatingHours.map {
                OperatingHourEmbeddable(
                    day = it.day.value,
                    openTime = it.openTime,
                    closeTime = it.closeTime,
                )
            },
        holidayType = this.holidayType.value,
        storeDescription = this.storeDescription.value,
    )
