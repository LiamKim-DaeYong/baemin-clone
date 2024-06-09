package com.demin.auth.application.port.`in`

import com.demin.auth.application.port.`in`.command.RegisterMemberCommand
import com.demin.auth.domain.Member
import com.demin.common.hexagonal.annotations.UseCase

@UseCase
interface RegisterMemberUseCase {
    fun registerMember(command: RegisterMemberCommand): Member
}