package com.demin.auth.application.port.out

import com.demin.auth.domain.Member

interface UpdateMemberPort {
    fun update(member: Member): Member
}