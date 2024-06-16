package com.demin.auth.adapter.outgoing.persistence.storeowner

import com.demin.auth.adapter.outgoing.persistence.useraccount.UserAccountJpaEntity
import com.demin.core.AuditableEntity
import com.demin.core.address.AddressJpaEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "store_owner")
class StoreOwnerJpaEntity(
    @Id
    @Column(name = "store_owner_id", length = 36, nullable = false, updatable = false)
    val id: String,

    @Column(nullable = false)
    val name: String,

    @Embedded
    val address: AddressJpaEntity,

    @Column(nullable = false)
    val phoneNumber: String,

    @MapsId
    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
    )
    @JoinColumn(name = "store_owner_id")
    val userAccount: UserAccountJpaEntity,
) : AuditableEntity()
