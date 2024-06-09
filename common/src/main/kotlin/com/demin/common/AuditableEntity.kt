package com.demin.common

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import kotlin.jvm.Transient

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditableEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    open var createdAt: LocalDateTime? = null
        protected set

    @CreatedBy
    @Column(updatable = false, length = 100)
    open var createdBy: String? = null
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    open var updatedAt: LocalDateTime? = null
        protected set

    @LastModifiedBy
    @Column(length = 100)
    open var updatedBy: String? = null
        protected set

    @Transient
    private var isNew: Boolean = true

    @PostPersist
    @PostLoad
    fun markNotNew() {
        this.isNew = false
    }

    @PrePersist
    fun markNew() {
        this.isNew = true
    }

    fun isNew(): Boolean {
        return isNew
    }
}
