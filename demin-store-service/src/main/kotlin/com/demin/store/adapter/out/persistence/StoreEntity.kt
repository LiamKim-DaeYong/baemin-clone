package com.demin.store.adapter.out.persistence

import com.demin.core.AuditableEntity
import com.demin.core.address.AddressJpaEntity
import com.demin.store.enums.DayType
import com.demin.store.enums.HolidayType
import jakarta.persistence.*
import java.time.LocalTime

@Entity
@Table(name = "store")
class StoreEntity(
    @Id
    @Column(name = "store_id", length = 36, nullable = false, updatable = false)
    val id: String,
    @Column(nullable = false)
    val storeName: String,
    @Embedded
    val storeAddress: AddressJpaEntity,
    @ElementCollection
    @CollectionTable(name = "operating_hours", joinColumns = [JoinColumn(name = "store_id")])
    val operatingHours: List<OperatingHourEmbeddable>,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val holidayType: HolidayType.Type,
    @Lob
    val storeDescription: String,
) : AuditableEntity()

@Embeddable
data class OperatingHourEmbeddable(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val day: DayType.Type,
    @Column(nullable = false)
    val openTime: LocalTime,
    @Column(nullable = false)
    val closeTime: LocalTime,
)
