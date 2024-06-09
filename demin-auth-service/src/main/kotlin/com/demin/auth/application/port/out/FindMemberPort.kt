package com.demin.auth.application.port.out

import com.demin.auth.domain.Member

interface FindMemberPort {
    fun findById(memberId: String): Member?
    fun findAll(): List<Member>
}