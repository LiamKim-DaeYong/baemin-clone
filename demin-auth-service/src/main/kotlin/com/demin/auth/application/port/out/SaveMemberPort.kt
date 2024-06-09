package com.demin.auth.application.port.out

import com.demin.auth.domain.Member

interface SaveMemberPort {
    fun save(member: Member): Member
}