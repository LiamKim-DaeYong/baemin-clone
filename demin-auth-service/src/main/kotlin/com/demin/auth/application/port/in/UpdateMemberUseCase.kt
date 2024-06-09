package com.demin.auth.application.port.`in`

import com.demin.auth.application.port.`in`.command.UpdateMemberCommand
import com.demin.auth.domain.Member
import com.demin.common.hexagonal.annotations.UseCase

@UseCase
interface UpdateMemberUseCase {
    fun updateMember(command: UpdateMemberCommand): Member
}