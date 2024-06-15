package com.demin.auth.adapter.out.persistence.customer

import com.demin.auth.adapter.out.persistence.useraccount.UserAccountJpaEntity
import com.demin.auth.domain.Customer.CustomerGrade.Grade
import com.demin.core.AuditableEntity
import com.demin.core.address.AddressJpaEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "customer")
class CustomerJpaEntity(
    @Id
    @Column(name = "customer_id", length = 36, nullable = false, updatable = false)
    val id: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val nickname: String,
    @Embedded
    val address: AddressJpaEntity,
    @Column(nullable = false)
    val phoneNumber: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val grade: Grade = Grade.BRONZE,
    @MapsId
    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
    )
    @JoinColumn(name = "customer_id")
    val userAccount: UserAccountJpaEntity,
) : AuditableEntity()
