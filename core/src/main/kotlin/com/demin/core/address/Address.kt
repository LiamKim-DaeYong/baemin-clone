package com.demin.core.address

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Address(
    @Column(nullable = false)
    val zipCode: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val detailAddress: String
)