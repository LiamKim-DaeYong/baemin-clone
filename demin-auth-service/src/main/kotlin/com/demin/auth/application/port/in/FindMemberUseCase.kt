package com.demin.auth.application.port.`in`

import com.demin.auth.domain.Member
import com.demin.core.hexagonal.annotations.UseCase

@UseCase
interface FindMemberUseCase {
    fun findMemberById(memberId: String): Member

    fun findAllMembers(): List<Member>
}